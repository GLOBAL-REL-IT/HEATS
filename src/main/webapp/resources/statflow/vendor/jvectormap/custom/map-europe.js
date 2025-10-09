// Europe
$(function () {
  $('#mapEurope').vectorMap({
    map: 'europe_mill',
    zoomOnScroll: false,
    series: {
      regions: [{
        values: gdpData,
        scale: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
        normalizeFunction: 'polynomial'
      }]
    },
    backgroundColor: 'transparent',
  });
});