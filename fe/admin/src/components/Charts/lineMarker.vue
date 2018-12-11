<template>
  <div :class="className"
    :id="id"
    :title="title"
    :xAxisData="[]"
    :legendData="[]"
    :yAxisDatas="[]"
    :done="false"
    :itemColor="[]"
    :itemBorderColor="[]"
    :style="{height:height,width:width}"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    id: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '200px'
    },
    height: {
      type: String,
      default: '200px'
    },
    title: {
      type: String,
      default: 'title'
    },
    legendData: {
      type: Array,
      default: []
    },
    xAxisData: {
      type: Array,
      default: []
    },
    yAxisDatas: {
      type: Array,
      default: []
    },
    itemColor: {
      type: Array,
      default: []
    },
    itemBorderColor: {
      type: Array,
      default: []
    },
    done: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      chart: null
    }
  },

  mounted() {
    this.initChart()
  },

  /** 监听数据变化 */
  watch: {
    done: function(val, oldVal) {
      if (val) {
        this.setOption()
      }
    }
  },

  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },

  methods: {

    initChart() {
      this.chart = echarts.init(document.getElementById(this.id))
      this.setOption()
    },

    setOption() {
      const legendLen = this.legendData.length
      const yAxisDatasLen = this.yAxisDatas.length
      if (legendLen !== yAxisDatasLen) {
        return
      }
      const series = []
      for (let i = 0; i < legendLen; i++) {
        series.push({
          name: this.legendData[i],
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 5,
          showSymbol: false,
          lineStyle: {
            normal: {
              width: 2
            }
          },
          itemStyle: {
            normal: {
              color: this.itemColor[i] || 'rgb(137,189,27)',
              borderColor: this.itemBorderColor[i] || 'rgba(137,189,2,0.2)',
              borderWidth: 12
            }
          },
          data: this.yAxisDatas[i]
        })
      }

      this.chart.setOption({
        backgroundColor: '0',
        title: {
          text: this.title,
          textStyle: {
            fontWeight: 'normal',
            fontSize: 16,
            color: '#F1F1F3'
          },
          left: '6%'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            lineStyle: {
              color: '#57617B'
            }
          }
        },
        legend: {
          icon: 'rect',
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          data: this.legendData,
          right: '4%',
          textStyle: {
            fontSize: 12,
            color: '#666'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          boundaryGap: false,
          axisLine: {
            lineStyle: {
              color: '#57617B'
            }
          },
          data: this.xAxisData
        }],
        yAxis: [{
          type: 'value',
          name: '单位（元）',
          axisTick: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: '#57617B'
            }
          },
          axisLabel: {
            margin: 10,
            textStyle: {
              fontSize: 14
            }
          },
          splitLine: {
            lineStyle: {
              color: '#57617B'
            }
          }
        }],
        series: series
      })
    }
  }
}
</script>
