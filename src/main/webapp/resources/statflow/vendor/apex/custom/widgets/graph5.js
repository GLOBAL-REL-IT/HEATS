// Graph 1
var options1 = {
  series: [{
    name: 'Completed',
    data: [1, 2, 3, 2, 3]
  }],
  chart: {
    type: 'line',
    width: 130,
    height: 75,
    sparkline: {
      enabled: true
    },
  },
  colors: ['#507dff'],
  stroke: {
    curve: 'smooth',
    width: 7,
  },
  fill: {
    type: 'gradient',
    gradient: {
      shade: 'light',
      type: 'vertical',
      shadeIntensity: 0.5,
      gradientToColors: ['#8e9fff'],
      inverseColors: false,
      opacityFrom: 0.8,
      opacityTo: 0.2,
    }
  },
  tooltip: {
    fixed: {
      enabled: false
    },
    x: {
      show: false
    },
    marker: {
      show: false
    }
  },
  xaxis: {
    type: 'day',
    categories: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return val
      }
    }
  },
};
var chart1 = new ApexCharts(document.querySelector("#taskStats1"), options1);
chart1.render();


// Graph 2
var options2 = {
  series: [{
    name: 'Pending',
    data: [1, 2, 3, 2, 3]
  }],
  chart: {
    type: 'line',
    width: 130,
    height: 75,
    sparkline: {
      enabled: true
    },
  },
  colors: ['#ff9a57'],
  stroke: {
    curve: 'smooth',
    width: 7,
  },
  fill: {
    type: 'gradient',
    gradient: {
      shade: 'light',
      type: 'vertical',
      shadeIntensity: 0.5,
      gradientToColors: ['#ffbc85'],
      inverseColors: false,
      opacityFrom: 0.8,
      opacityTo: 0.2,
    }
  },
  tooltip: {
    fixed: {
      enabled: false
    },
    x: {
      show: false
    },
    marker: {
      show: false
    }
  },
  xaxis: {
    type: 'day',
    categories: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return val
      }
    }
  },
};
var chart2 = new ApexCharts(document.querySelector("#taskStats2"), options2);
chart2.render();

// Graph 3
var options3 = {
  series: [{
    name: 'In Progess',
    data: [1, 2, 3, 2, 3]
  }],
  chart: {
    type: 'line',
    width: 130,
    height: 75,
    sparkline: {
      enabled: true
    },
  },
  colors: ['#50c6ff'],
  stroke: {
    curve: 'smooth',
    width: 7,
  },
  fill: {
    type: 'gradient',
    gradient: {
      shade: 'light',
      type: 'vertical',
      shadeIntensity: 0.5,
      gradientToColors: ['#92ddff'],
      inverseColors: false,
      opacityFrom: 0.8,
      opacityTo: 0.2,
    }
  },
  tooltip: {
    fixed: {
      enabled: false
    },
    x: {
      show: false
    },
    marker: {
      show: false
    }
  },
  xaxis: {
    type: 'day',
    categories: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return val
      }
    }
  },
};
var chart3 = new ApexCharts(document.querySelector("#taskStats3"), options3);
chart3.render();

// Graph 4
var options4 = {
  series: [{
    name: 'Closed',
    data: [1, 2, 3, 2, 3]
  }],
  chart: {
    type: 'line',
    width: 130,
    height: 75,
    sparkline: {
      enabled: true
    },
  },
  colors: ['#4ade80'],
  stroke: {
    curve: 'smooth',
    width: 7,
  },
  fill: {
    type: 'gradient',
    gradient: {
      shade: 'light',
      type: 'vertical',
      shadeIntensity: 0.5,
      gradientToColors: ['#92efb7'],
      inverseColors: false,
      opacityFrom: 0.8,
      opacityTo: 0.2,
    }
  },
  tooltip: {
    fixed: {
      enabled: false
    },
    x: {
      show: false
    },
    marker: {
      show: false
    }
  },
  xaxis: {
    type: 'day',
    categories: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return val
      }
    }
  },
};
var chart4 = new ApexCharts(document.querySelector("#taskStats4"), options4);
chart4.render();