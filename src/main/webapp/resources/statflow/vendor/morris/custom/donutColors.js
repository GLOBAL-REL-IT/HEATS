// Morris Donut
Morris.Donut({
  element: "donutColors",
  data: [
    { value: 30, label: "foo" },
    { value: 15, label: "bar" },
    { value: 10, label: "baz" },
    { value: 5, label: "A really really long label" },
  ],
  backgroundColor: "#17181c",
  labelColor: "#17181c",
  colors: [
    "#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"
  ],
  resize: true,
  hideHover: "auto",
  gridLineColor: "#dfd6ff",
  formatter: function (x) {
    return x + "%";
  },
});
