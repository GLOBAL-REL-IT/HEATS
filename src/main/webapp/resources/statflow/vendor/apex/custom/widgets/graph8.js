var options = {
  chart: {
    height: 320,
    type: 'radialBar',
  },
  labels: ['Current Customers', 'New Customers', 'Targeted Customers'],
  series: [75, 50, 25],
  plotOptions: {
    radialBar: {
      dataLabels: {
        name: {
          show: true,
        },
        value: {
          show: true,
          formatter: function (val) {
            return val + '%'
          }
        },
        total: {
          show: true,
          label: 'Total'
        }
      }
    }
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
}
var chart = new ApexCharts(
  document.querySelector("#graph8"),
  options
);
chart.render();