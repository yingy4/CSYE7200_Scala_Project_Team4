
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
var productCategory = new Array(20);
var cityACount = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
var cityBCount = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
var cityCCount = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
 var productJsonObject;

var currentUsersCount = [];//[["1002181",0]];

    function availableAnalytics(salesItemRow){

       // console.log(product);
       productJsonObject = JSON.parse(salesItemRow);
debugger;
if(productJsonObject[0][0][1]=='-')
{
   allProductsCount = productJsonObject;
}

else if(productJsonObject!=undefined && productJsonObject[0][0].length==2)
{
    productCategoryCitydataUpdate()
}

else{
//         var foundProduct = allProductsCount.findIndex(function(element) {
//                   return element[1] == allProductsCount.product_id;
//               });
//
//        if(foundProduct == -1){
//               var product = [productJsonObject.product_id,1];
//               allProductsCount.push(product);
//           }
//           else{
//               allProductsCount[foundProduct][1] = allProductsCount[foundProduct][1] + 1;
//         }



        var foundUser = currentUsersCount.findIndex(function(element) {
            return element[0] == String(productJsonObject.user_id);
        });
        debugger;


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

//productCategoryCityDataUpdate

function productCategoryCitydataUpdate()
{
    for(var i= 0;i<productJsonObject.length;i++){
    var index = productJsonObject[i][0][0];
    console.log(index);
    var city = productJsonObject[i][0][1];
    console.log(city);
    if(city == 'A')
    {
        cityACount[index] =productJsonObject[i][1];
    }
    if(city == 'B')
    {
        cityBCount[index] =productJsonObject[i][1];
    }
    if(city == 'C')
    {
        cityCCount[index] =productJsonObject[i][1];
    }
}
productCategoryCitygraph();
}


function productCategoryCitygraph()
{
Highcharts.setOptions({
    plotOptions: {
        series: {
            animation: false
        }
    }
});

Highcharts.chart('categoryvscity', {
    chart: {
        type: 'bar'
    },
    title: {
        text: 'Product Category VS City'
    },
    xAxis: {
        categories: ['1', '2', '3','4','5','6','7','8','9','10','11', '12', '13','14','15','16','17','18','19','20']
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Total products across different Category and City'
        }
    },
    plotOptions : {
                series: {
                    animation : false
                }

                },
    legend: {
        reversed: true
    },
    plotOptions: {
        series: {
            stacking: 'normal'
        }
    },
    series: [{
        name: 'City A',
        data: cityACount
    }, {
        name: 'City B',
        data: cityBCount
    }, {
        name: 'City C',
        data: cityCCount
    }]
});

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
                var product = allProductsCount.find(function(e){return e[0][0] == productsOnChart[0][0]});
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
            var product = allProductsCount.find(function(e){return e[0][0] == productsOnChart[0][0]});
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

