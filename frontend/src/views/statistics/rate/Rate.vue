
<template>
  <a-card :bordered="false" class="card-area" style="width: 100%">
    <div style="background:#ECECEC; padding:30px;margin-top: 30px;margin-bottom: 30px">
      <a-row :gutter="30">
        <a-col :span="8" v-for="(item, index) in areaList" :key="index">
          <a-card :bordered="false" class="area-card">
            <div class="area-title">{{ item.area }}</div>
            <apexchart
              type="pie"
              height="250"
              :options="chartOptions[index]"
              :series="chartSeries[index]"
            ></apexchart>
            <div class="area-stats">
              <a-statistic
                title="总车位"
                :value="item.total"
                valueStyle="color: #3f8600"
              />
              <a-statistic
                title="空闲车位"
                :value="item.free"
                valueStyle="color: #1890ff" style="margin-top: 15px"
              />
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </a-card>
</template>

<script>import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'Rate',
  data () {
    return {
      areaList: [],
      chartOptions: [],
      chartSeries: []
    }
  },
  mounted () {
    this.queryParkByArea()
  },
  methods: {
    queryParkByArea () {
      this.$get(`/cos/park-order-info/queryParkByArea`).then((r) => {
        this.areaList = r.data || []
        this.initCharts()
      })
    },
    initCharts () {
      this.chartOptions = []
      this.chartSeries = []

      this.areaList.forEach((item, index) => {
        const occupied = item.total - item.free

        this.chartSeries.push([item.free, occupied])

        this.chartOptions.push({
          chart: {
            type: 'pie',
            height: 250
          },
          labels: ['空闲', '占用'],
          colors: ['#52c41a', '#ff4d4f'],
          legend: {
            position: 'bottom',
            fontSize: '12px'
          },
          plotOptions: {
            pie: {
              donut: {
                size: '0%'
              }
            }
          },
          dataLabels: {
            enabled: true,
            style: {
              fontSize: '14px',
              fontWeight: 'bold'
            }
          },
          tooltip: {
            y: {
              formatter: (val) => val + ' 个车位'
            }
          },
          title: {
            text: `${item.area} - 车位分布`,
            align: 'center',
            style: {
              fontSize: '14px'
            }
          }
        })
      })
    }
  }
}
</script>

<style scoped>.area-card {
  margin-bottom: 20px;
  text-align: center;
}

.area-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 15px;
  color: rgba(0, 0, 0, 0.85);
}

.area-stats {
  margin-top: 20px;
  padding: 10px;
  background: #fafafa;
  border-radius: 4px;
}
</style>
