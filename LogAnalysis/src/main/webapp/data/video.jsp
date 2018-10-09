<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
 <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<body>
  <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1000px;height:400px;"></div>
    <div id="main2" style="width: 1000px;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
       var arr;
       var nums=[];
       var values=[];
        $.ajax({
        	"url":"http://localhost:8080/LogAnalysis/dataServlet?method=number",
        	"async":false,
  			"type":"get",
        	"dataType":"json",
        	"success": function(data){
        		console.log(data);
        		arr=data;
        	}
        });
        for(var i=0;i<arr.length;i++)
        {
        	nums.push(arr[i].type_id);
        	values.push(arr[i].number);
        }
        console.log(nums);
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '最受欢迎的视频'
            },
            tooltip: {},
            legend: {
                data:['数量']
            },
            xAxis: {
                data: nums
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: values
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
    
    
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
       var arr;
       var nums=[];
       var values=[];
        $.ajax({
        	"url":"http://localhost:8080/LogAnalysis/dataServlet?method=traffic",
        	"async":false,
  			"type":"get",
        	"dataType":"json",
        	"success": function(data){
        		console.log(data);
        		arr=data;
        	}
        });
        for(var i=0;i<arr.length;i++)
        {
        	nums.push(arr[i].type_id);
        	values.push(arr[i].number);
        }
        console.log(nums);
        var myChart = echarts.init(document.getElementById('main2'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '最受欢迎的视频'
            },
            tooltip: {},
            legend: {
                data:['数量']
            },
            xAxis: {
                data: nums
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: values
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
</body>
</html>