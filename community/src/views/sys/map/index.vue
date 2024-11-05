<template>
  <div class="app-container">
    <el-form :inline="true" :model="listQuery">
      <el-form-item label="身份证号">
        <el-select v-model="listQuery.idcard" filterable placeholder="请选择" style="width:150px" clearable>
          <el-option
            v-for="park in communityList"
            :key="park.communityId"
            :label="park.communityName"
            :value="park.communityId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="listQuery.userName" placeholder="输入姓名" clearable />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="listQuery.mobile" placeholder="输入手机号查询" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="initData">搜索</el-button>
      </el-form-item>
      <el-row>
        <el-form-item>
          <el-button v-has="'sys:person:add'" type="primary" icon="el-icon-plus" @click="addOrUpdateHandle(1)">添加</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-has="'sys:person:info'" type="success" icon="el-icon-edit" :disabled="ids.length <= 0" @click="addOrUpdateHandle()">编辑</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-has="'sys:person:del'" type="danger" icon="el-icon-delete" :disabled="ids.length <= 0" @click="deleteHandle()">删除</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-upload2" @click="importExcelHandle">导入</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-download" @click="handleExcel">导出</el-button>
        </el-form-item>
      </el-row>
    </el-form>
    <el-table ref="topictable" v-loading="loading" :height="tableHeight" :row-style="rowStyle" size="mini" border :data="tableData" @selection-change="selectionChangeHandle">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column prop="userName" label="姓名" width="80" align="center" />
      <el-table-column prop="team" label="队别" width="50" align="center" />
      <el-table-column prop="mobile" label="手机号码" show-overflow-tooltip align="center" width="100" />
      <el-table-column prop="domicile" label="户籍" show-overflow-tooltip align="center" width="100" />
      <el-table-column prop="idcard" label="身份证号" show-overflow-tooltip align="center" width="200" />
      <el-table-column prop="householderrelationship" label="户主关系" show-overflow-tooltip align="center" width="100" />
      <el-table-column prop="ishouseholder" label="是否为户主" width="100" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.state === 1">户主</p>
          <p v-else-if="scope.row.state === 2">不是户主</p>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="initData" />
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="initData" />
    <ImportExcel v-if="importVisible" ref="importExcel" @refreshDataList="initData" />
    <Face v-if="faceVisible" ref="face" @refreshDataList="initData" />
  </div>
</template>

<script>
import { getPersonList, del, exportExcel, getCommunityList } from '@/api/sys/person'
import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update'
import ImportExcel from './importExcel'
import Face from './face'

export default {
  name: 'Person',
  components: {
    Pagination,
    AddOrUpdate,
    ImportExcel,
    Face
  },
  data() {
    return {
      listQuery: {
        page: 1,
        limit: 10,
        userName: '',
        team:'',
        mobile: '',
        domicile:'',
        idcard:'',
        householderrelationship:'',
        ishouseholder:''

      },
      total: 0,
      loading: true,
      downloadLoading: false,
      ids: [],
      tableData: [],
      communityList: [],
      addOrUpdateVisible: false,
      importVisible: false,
      payMoneyVisible: false,
      faceVisible: false
    }
  },
  created() {
    this.initData()
    // this.getAllAgent()
  },
  mounted() {
    // console.log(window.innerHeight)
    // console.log(this.$refs.topictable.$el)
    // console.log(this.$refs.topictable.$el.offsetTop)
    this.tableHeight = window.innerHeight - this.$refs.topictable.$el.offsetTop - 50
    // console.log(this.tableHeight)
  },
  methods: {
    initData() {
      this.loading = true
      getPersonList(this.listQuery).then(res => {
        this.agentList = res.data.agentList
        this.communityList = res.data.communityList
        if (this.$store.state.user.userType === 2) {
          this.listQuery.agentId = this.agentList[0].agentId
          // this.getCommunityList(this.listQuery.agentId)
        }
        this.tableData = res.data.pageList.list
        this.total = res.data.pageList.totalCount
        this.loading = false
      })
    },
    getCommunityList() {
      getCommunityList().then(res => {
        if (res.code === 200) {
          this.communityList = []
          this.communityList = res.data
          this.listQuery.communityId = ''
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err)
      })
    },
    // 多选
    selectionChangeHandle(val) {
      this.ids = val
    },
    // 添加和编辑
    addOrUpdateHandle(flag) {
      this.addOrUpdateVisible = true
      if (flag !== 1) {
        if (this.ids.length > 1) {
          this.$message.error('编辑操作只能选择单条数据')
          return
        }
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(this.ids[0].personId)
        })
      } else {
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(0)
        })
      }
    },
    cameraHandle() {
      if (this.ids.length > 1) {
        this.$message.error('人脸采集只能选择单条数据')
        return
      }
      if (this.ids[0].state === 2) {
        this.$message.error('人脸识别已通过，不需要重复识别')
        return
      }
      this.faceVisible = true
      this.$nextTick(() => {
        this.$refs.face.init(this.ids[0].personId)
      })
    },
    formatDate(row, column) {
      const daterc = row[column.property]
      console.log(daterc)
      if (daterc != null) {
        const dateMat = new Date(daterc)
        const year = dateMat.getFullYear()
        const month = dateMat.getMonth() + 1
        const day = dateMat.getDate()
        // const hh = dateMat.getHours()
        // const mm = dateMat.getMinutes()
        // const ss = dateMat.getSeconds()
        // const timeFormat = year + '/' + month + '/' + day + ' ' + hh + ':' + mm + ':' + ss
        const timeFormat = year + '-' + month + '-' + day
        return timeFormat
      }
    },
    // 导入
    importExcelHandle() {
      this.importVisible = true
      this.$nextTick(() => {
        this.$refs.importExcel.init()
      })
    },
    // 导出
    handleExcel() {
      this.$confirm('是否确认导出所有数据项?').then(res => {
        this.downloadLoading = true
        exportExcel(this.listQuery).then(res => {
          location.href = process.env.VUE_APP_BASE_API + '/IdeaPorject/IntelligentCommunity/upload/excel/' + res.data
        })
      }).catch(() => {})
    },
    // 删除
    deleteHandle(id) {
      const userIds = id ? [id] : this.ids.map(item => {
        return item.personId
      })
      this.$confirm('确定删除 [id='+userIds+'] 的数据?').then(res => {
        del(userIds).then(res => {
          this.loading = true
          if (res.code === 200) {
            this.$message.success(res.msg)
            this.loading = false
            this.initData()
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    rowStyle({ row, rowIndex }) {
      if (this.ids.includes(row)) {
        return { 'background-color': 'rgb(185, 211, 249)' }
      }
    }
  }
}
</script>
