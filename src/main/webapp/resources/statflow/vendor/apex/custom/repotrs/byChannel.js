var options = {
  chart: {
    height: 280,
    type: 'bar',
    toolbar: {
      show: false,
    },
  },
  plotOptions: {
    bar: {
      horizontal: true,
      columnWidth: '20%',
      distributed: true,
      borderRadius: 10,
    }
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    show: true,
    width: 0,
    colors: ['rgba(62, 68, 81, .3)']
  },
  grid: {
    borderColor: "#d8dee6",
    strokeDashArray: 5,
    xaxis: {
      lines: {
        show: false,
      }
    },
    yaxis: {
      lines: {
        show: true,
      }
    },
    padding: {
      top: 0,
      right: 0,
      bottom: 0,
      left: 0
    },
  },
  series: [{
    data: [2000, 3000, 4000, 5000, 6000]
  }],
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
  xaxis: {
    categories: ["Organic", "Search", "TV Ads", "Social", "Video"],
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return val + ' Visits'
      }
    }
  },
}

var chart = new ApexCharts(
  document.querySelector("#byChannel"),
  options
);

chart.render();