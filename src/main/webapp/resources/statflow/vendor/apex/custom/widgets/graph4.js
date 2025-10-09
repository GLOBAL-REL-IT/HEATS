var options = {
  chart: {
    height: 320,
    type: 'area',
    toolbar: {
      show: false,
    },
    dropShadow: {
      enabled: true,
      top: 5,
      left: 0,
      blur: 8,
      opacity: 0.2
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
    data: [3500, 2800, 4200, 3800, 5100]
  }, {
    name: 'Expired',
    data: [1800, 2200, 3400, 2600, 2700]
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
    type: 'category',
    categories: ["Q1", "Q2", "Q3", "Q4", "Q5"],
  },
  colors: ["#3b82f6", "#f97316"],
  fill: {
    type: 'gradient',
    gradient: {
      shade: 'light',
      type: "vertical",
      shadeIntensity: 0.3, // Reduced from 0.5
      gradientToColors: ["#60a5fa", "#fb923c"],
      inverseColors: false,
      opacityFrom: 0.5, // Reduced from 0.8
      opacityTo: 0.1, // Reduced from 0.2
      stops: [0, 100]
    }
  },
  yaxis: {
    show: false,
  },
  markers: {
    size: 4,
    opacity: 0.9,
    colors: ["#3b82f6", "#f97316"],
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
    theme: 'dark'
  }
}

var chart = new ApexCharts(
  document.querySelector("#graph4"),
  options
);

chart.render();