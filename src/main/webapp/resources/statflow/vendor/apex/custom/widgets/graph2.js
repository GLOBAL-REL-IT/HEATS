var options = {
  chart: {
    height: 270,
    type: 'area',
    toolbar: {
      show: false,
    },
    dropShadow: {
      enabled: true,
      opacity: 0.2,
      blur: 5,
      left: 5,
      top: 5
    }
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
    data: [320, 450, 580, 620, 750, 580, 680]
  }, {
    name: 'Expired',
    data: [120, 230, 380, 350, 480, 420, 350]
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
  colors: ["#507DFF", "#6A90FF"],
  fill: {
    type: 'gradient',
    gradient: {
      shade: 'light',
      type: 'vertical',
      shadeIntensity: 0.5,
      gradientToColors: ['#83A3FF', '#B7C9FF'],
      inverseColors: false,
      opacityFrom: 0.8,
      opacityTo: 0.2,
      stops: [0, 100]
    }
  },
  yaxis: {
    show: false,
  },
  markers: {
    size: 0,
    opacity: 0.2,
    colors: ["#507DFF", "#6A90FF"],
    strokeColor: "#fff",
    strokeWidth: 2,
    hover: {
      size: 7,
    }
  },
  tooltip: {
    x: {
      format: 'dd/MM/yy'
    },
  }
}

var chart = new ApexCharts(
  document.querySelector("#graph2"),
  options
);

chart.render();