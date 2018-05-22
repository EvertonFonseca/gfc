function (labels){

var data = {
    labels,
    series: [
        [5, 4, 3, 7, 5, 10, 3, 4, 8, 10, 6, 8],
        [3, 2, 9, 5, 4, 6, 4, 6, 7, 8, 7, 4]
    ]
};
var options = {
    seriesBarDistance: 10
};

var responsiveOptions = [
    ['screen and (max-width: 640px)', {
            seriesBarDistance: 5,
            axisX: {
                labelInterpolationFnc: function (value) {
                    return value[0];
                }
            }
        }]
];
Wt.signal(labels)

new Chartist.Bar('#ct-bar', data, options, responsiveOptions);

}