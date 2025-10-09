// Stacked Bar Chart
Morris.Bar({
  element: "stackedBar",
  data: [
    { x: "2023 Q1", y: 3, z: 2, a: 3 },
    { x: "2023 Q2", y: 2, z: null, a: 1 },
    { x: "2023 Q3", y: 0, z: 2, a: 1 },
    { x: "2023 Q4", y: 2, z: 3, a: 3 },
    { x: "2022 Q1", y: 3, z: 2, a: 3 },
    { x: "2022 Q2", y: 2, z: null, a: 1 },
    { x: "2022 Q3", y: 0, z: 2, a: 4 },
    { x: "2022 Q4", y: 2, z: 3, a: 3 },
  ],
  xkey: "x",
  ykeys: ["y", "z", "a"],
  labels: ["Y", "Z", "A"],
  stacked: true,
  hideHover: "auto",
  resize: true,
  gridLineColor: "#dfd6ff",
  barColors: [
    "#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"
  ],
});
