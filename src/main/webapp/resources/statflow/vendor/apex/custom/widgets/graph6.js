var options = {
  chart: {
    height: 265,
    type: 'area',
    toolbar: {
      show: false,
    },
    dropShadow: {
      enabled: true,
      opacity: 0.1,
      blur: 5,
      left: -10,
      top: 10
    },
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'smooth',
    width: 3
  },
  series: [{
    name: 'Claimed',
    data: [200, 300, 500, 600, 800, 400, 700]
  }, {
    name: 'Expired',
    data: [150, 250, 300, 400, 500, 300, 500]
  }],
  grid: {
    borderColor: "#d8dee6",
    strokeDashArray: 5,
    xaxis: {
      lines: {
        show: true
      }
    },
    yaxis: {
      lines: {
        show: false,
      }
    },
    padding: {
      top: 0,
      right: 30,
      bottom: 0,
      left: 30
    },
  },
  xaxis: {
    type: 'day',
    categories: ["Sun", "Mon", "Tue", "Wedn", "Thu", "Fri", "Sat"],
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
  yaxis: {
    show: false,
  },
  markers: {
    size: 0,
    opacity: 0.2,
    colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
    strokeColor: "#fff",
    strokeWidth: 2,
    hover: {
      size: 7,
    }
  },
  dropShadow: {
    enabled: true,
    opacity: 0.1,
    blur: 5,
    left: -10,
    top: 10
  },
  tooltip: {
    x: {
      format: 'dd/MM/yy'
    },
  }
}

var chart = new ApexCharts(
  document.querySelector("#graph6"),
  options
);

chart.render();