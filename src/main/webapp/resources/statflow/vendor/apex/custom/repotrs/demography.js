var options = {
  series: [42, 47, 52, 58, 65],
  chart: {
    width: 320,
    height: 320,
    type: 'polarArea',
    fontFamily: 'Poppins, sans-serif',
    toolbar: {
      show: false
    },
    animations: {
      enabled: true,
      easing: 'easeinout',
      speed: 800
    }
  },
  labels: ['13-20', '21-30', '31-40', '41-49', '50+'],
  fill: {
    opacity: 0.85,
    gradient: {
      enabled: true
    }
  },
  stroke: {
    width: 2,
    colors: ['#ffffff']
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF"],
  yaxis: {
    show: false
  },
  legend: {
    position: 'bottom',
    fontSize: '14px',
    markers: {
      radius: 3
    }
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return val + " Million"
      }
    },
    theme: 'dark'
  },
  dataLabels: {
    enabled: true,
    formatter: function (val) {
      return Math.round(val) + "%"
    },
    style: {
      fontSize: '12px',
      fontWeight: 'bold'
    }
  },
  plotOptions: {
    polarArea: {
      rings: {
        strokeWidth: 0
      },
      spokes: {
        strokeWidth: 0
      },
      offsetY: 0,
      offsetX: 0
    }
  },
  responsive: [{
    breakpoint: 480,
    options: {
      chart: {
        width: 280
      },
      legend: {
        position: 'bottom'
      }
    }
  }]
};

var chart = new ApexCharts(document.querySelector("#demography"), options);
chart.render();