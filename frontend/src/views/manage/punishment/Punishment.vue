
<template>
  <a-card :bordered="false" class="card-area">
    <a-form layout="vertical">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item
            label="最大免费停留时长（分钟）"
            :labelCol="{span: 8}"
            :wrapperCol="{span: 16}">
            <a-input-number
              v-model="form.minuteMax"
              :min="0"
              :max="1440"
              :step="10"
              placeholder="请输入免费停留时长"              style="width: 100%">
              <a-icon slot="addonBefore" type="clock-circle" />
            </a-input-number>
            <div class="form-tip">
              提示：用户停车后在此时间内免费，超过此时长将按以下单价收费
            </div>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="超时单价（每 10 分钟）"
            :labelCol="{span: 8}"
            :wrapperCol="{span: 16}">
            <a-input-number
              v-model="form.minntePriceUnit"
              :min="0"
              :max="999"
              :step="0.5"
              :precision="2"
              placeholder="请输入超时单价"              style="width: 100%">
              <a-icon slot="addonBefore" type="pay-circle" />
            </a-input-number>
            <div class="form-tip">
              提示：超过免费时长后，每 10 分钟的收费标准（单位：元）
            </div>
          </a-form-item>
        </a-col>
      </a-row>
      <a-divider />
      <a-row>
        <a-col :span="24" style="text-align: center">
          <a-button type="primary" @click="handleSubmit" :loading="loading" icon="save">
            保存设置
          </a-button>
          <a-button @click="handleReset" style="margin-left: 10px" icon="reload">
            重置
          </a-button>
        </a-col>
      </a-row>
    </a-form>

    <!-- 信息展示卡片 -->
    <a-divider orientation="left">当前设置说明</a-divider>
    <a-row :gutter="16">
      <a-col :span="8">
        <a-statistic
          title="当前免费时长"
          :value="form.minuteMax"
          suffix="分钟"
          :valueStyle="{ color: '#3f8600' }">
          <template #prefix>
            <a-icon type="check-circle" />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="8">
        <a-statistic
          title="超时单价"
          :value="form.minntePriceUnit"
          suffix="元/10 分钟"
          :valueStyle="{ color: '#1890ff' }">
          <template #prefix>
            <a-icon type="shopping" />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="8">
        <a-statistic
          title="每小时收费"
          :value="hourlyRate"
          suffix="元"
          :valueStyle="{ color: '#cf1322' }">
          <template #prefix>
            <a-icon type="calculator" />
          </template>
        </a-statistic>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>export default {
  name: 'Punishment',
  data () {
    return {
      loading: false,
      form: {
        minuteMax: null,
        minntePriceUnit: null
      },
      originalData: {}
    }
  },
  computed: {
    hourlyRate () {
      if (!this.form.minntePriceUnit) return 0
      return (this.form.minntePriceUnit * 6).toFixed(2)
    }
  },
  mounted () {
    this.queryPriceSet()
  },
  methods: {
    queryPriceSet () {
      this.loading = true
      this.$get('/cos/punishment-info/detail/1').then((r) => {
        if (r.data && r.data.data) {
          const data = r.data.data
          this.form.minuteMax = data.minuteMax
          this.form.minntePriceUnit = data.minntePriceUnit
          this.originalData = { ...data }
        }
      }).catch(() => {
        this.$message.error('获取设置失败')
      }).finally(() => {
        this.loading = false
      })
    },
    handleSubmit () {
      if (!this.form.minuteMax && this.form.minuteMax !== 0) {
        this.$message.warning('请输入最大免费停留时长')
        return
      }
      if (!this.form.minntePriceUnit && this.form.minntePriceUnit !== 0) {
        this.$message.warning('请输入超时单价')
        return
      }

      this.loading = true
      this.$put('/cos/punishment-info', {
        id: 1,
        minuteMax: this.form.minuteMax,
        minntePriceUnit: this.form.minntePriceUnit
      }).then(() => {
        this.$message.success('保存设置成功')
        this.originalData = { ...this.form }
      }).catch(() => {
        this.$message.error('保存设置失败')
      }).finally(() => {
        this.loading = false
      })
    },
    handleReset () {
      this.form.minuteMax = this.originalData.minuteMax
      this.form.minntePriceUnit = this.originalData.minntePriceUnit
      this.$message.success('已重置为原始值')
    }
  }
}
</script>

<style scoped>.card-area {
  background-color: #fff;
}

.form-tip {
  font-size: 12px;
  color: rgba(0, 0, 0, 0.45);
  margin-top: 8px;
}

.ant-divider {
  margin: 24px 0;
}
</style>
