var options = {
  series: [
    {
      name: 'Completed',
      data: [
        {
          x: 'Mobile App',
          y: 12,
          goals: [
            {
              name: 'Expected',
              value: 14,
              strokeWidth: 2,
              strokeDashArray: 2,
              strokeColor: '#ac5cdd'
            }
          ]
        },
        {
          x: 'Landing Page',
          y: 44,
          goals: [
            {
              name: 'Expected',
              value: 54,
              strokeWidth: 5,
              strokeHeight: 10,
              strokeColor: '#ac5cdd'
            }
          ]
        },
        {
          x: 'Dashboard Design',
          y: 54,
          goals: [
            {
              name: 'Expected',
              value: 52,
              strokewidth: 0,
              strokeHeight: 0,
              strokeLineCap: 'round',
              strokeColor: '#ac5cdd'
            }
          ]
        },
        {
          x: 'Food Order App',
          y: 81,
          goals: [
            {
              name: 'Expected',
              value: 66,
              strokewidth: 0,
              strokeHeight: 0,
              strokeLineCap: 'round',
              strokeColor: '#ac5cdd'
            }
          ]
        },
        {
          x: 'Can Rent App',
          y: 67,
          goals: [
            {
              name: 'Expected',
              value: 70,
              strokeWidth: 5,
              strokeHeight: 10,
              strokeColor: '#ac5cdd'
            }
          ]
        }
      ]
    }
  ],
  chart: {
    height: 270,
    type: 'bar',
    toolbar: {
      show: false,
    },
  },
  dropShadow: {
    enabled: true,
    opacity: 0.1,
    blur: 5,
    left: -10,
    top: 10
  },
  plotOptions: {
    bar: {
      horizontal: true,
    }
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
  dataLabels: {
    formatter: function (val, opt) {
      const goals =
        opt.w.config.series[opt.seriesIndex].data[opt.dataPointIndex]
          .goals

      if (goals && goals.length) {
        return `${val} / ${goals[0].value}`
      }
      return val
    }
  },
  legend: {
    show: true,
    showForSingleSeries: true,
    customLegendItems: ['Completed', 'Expected'],
    markers: {
      fillColors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"]
    }
  }
};

var chart = new ApexCharts(document.querySelector("#graph3"), options);
chart.render();