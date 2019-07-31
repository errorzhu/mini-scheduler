# mini scheduler

微型的基于图的调度引擎，与业务分离。

两种使用方式

1 自己的业务类实现 IWork 接口，通过api 构建节点 和 边，使用调度器运行图

2 支持读入json格式的图配置文件，通过指定业务类的className，调度器运行时调用相应业务类



具体使用 ，请见 SchedulerTest 测试类