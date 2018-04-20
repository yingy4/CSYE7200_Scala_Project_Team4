
console.log("I am here")
var url = "ws://localhost:9000/streamData";

var streamSocket = new WebSocket(url);
streamSocket.onmessage = function (event) {

console.log(event.data);
debugger;
var data = event.data;

availableAnalytics(data);
};
streamSocket.onopen = function() {
streamSocket.send("streaming");

};



// main logic
var productsOnChart = [], allProductsCount=[];
var currentUsersCount = [];//[["1002181",0]];

    function availableAnalytics(salesItemRow){

        console.log(product);
       var productJsonObject = JSON.parse(salesItemRow);


         var foundProduct = allProductsCount.findIndex(function(element) {
                   return element[1] == allProductsCount.product_id;
               });

        if(foundProduct == -1){
               var product = [productJsonObject.product_id,1];
               allProductsCount.push(product);
           }
           else{
               allProductsCount[foundProduct][1] = allProductsCount[foundProduct][1] + 1;
         }



        var foundUser = currentUsersCount.findIndex(function(element) {
            return element[0] == String(productJsonObject.user_id);
        });
        debugger;




        //Product is not present
        if(currentUsersCount.length < 15){


        if(foundUser == -1){
            var user = [String(productJsonObject.user_id),1];
            currentUsersCount.push(user);
        }
        else{
            currentUsersCount[foundUser][1] = currentUsersCount[foundUser][1] + 1;
        }
        } else{
            resetUsersChart();
        }
        refreshChart();

    }


    function resetUsersChart(){
        currentUsersCount = [];
    }

    function addProduct() {
    var productId = document.getElementById('product_id').value;
        console.log("Adding Product: "+productId);
        productsOnChart = [];
        var newProduct = [productId.toString(),1];
        productsOnChart.push(newProduct);
        document.getElementById('product_id').value = "";
        singleproduct();
    }




//all Users Highchart

function refreshChart(){
    Highcharts.chart('container', {
    chart: {
        type: 'column'
    },
    title: {
        text: 'Real-time Users Buying Products Chart'
    },
    subtitle: {
        text: 'Current Users Buying Count'
    },
        plotOptions : {
        column: {
            animation : false
        }

        },
    xAxis: {
    categories:[],
        type: 'category',
        labels: {
            rotation: -45,
            style: {
                fontSize: '13px',
                fontFamily: 'Verdana, sans-serif'
            }
        }
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Users Buying Products Count'
        }
    },
    legend: {
        enabled: false
    },
    tooltip: {
        pointFormat: 'Current Product Buying count: <b>{point.y:.1f} </b>'
    },
    series: [{
        name: 'Population',
        data: currentUsersCount,
        dataLabels: {
            enabled: true,
            rotation: -90,
            color: '#FFFFFF',
            align: 'right',
            format: '{point.y:.1f}', // one decimal
            y: 10, // 10 pixels down from the top
            style: {
                fontSize: '13px',
                fontFamily: 'Verdana, sans-serif'
            }
        }
    }]
});
}

//singleProduct Analysis

function singleproduct(){
Highcharts.setOptions({
    global: {
        useUTC: false
    }
});

Highcharts.chart('singleProduct', {
    chart: {
        type: 'spline',
        animation: Highcharts.svg, // don't animate in old IE
        marginRight: 10,
        events: {
            load: function () {

                // set up the updating of the chart each second
                var series = this.series[0];
                var product = allProductsCount.find(function(e){return e[0] == productsOnChart[0][0]});
                if(product != undefined){
                    setInterval(function () {
                        var x = (new Date()).getTime(), // current time
                            y = product[1];
                        series.addPoint([x, y], true, true);
                    }, 1000);
                }
            }
        }
    },
    title: {
        text: 'Live Product Sales Ticker :'+ productsOnChart[0][0]
    },
    xAxis: {
        type: 'datetime',
        tickPixelInterval: 150
    },
    yAxis: {
        title: {
            text: 'Value'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + '</b><br/>' +
                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                Highcharts.numberFormat(this.y, 2);
        }
    },
    legend: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
    series: [{
        name: 'Currently sold',
        data: (function () {
            // generate an array of random data
            var data = [],
                time = (new Date()).getTime(),
                i;

            for (i = -19; i <= 0; i += 1) {
            var product = allProductsCount.find(function(e){return e[0] == productsOnChart[0][0]});
            if(product != undefined){
                data.push({
                    x: time + i * 1000,
                    y: product[1]
                });
            }

            }
            return data;
        }())
    }]
});
}

