package cn.itcast.zookeeper;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

public class ZookeeperTest {
    public static void main(String[] args) throws  Exception{
        //创建zookeeper客户端
        String connectStr = "192.168.100.10:2181,192.168.100.20:2181,192.168.100.30:2181";
        //参数1：zk服务地址；参数2：会话超过时间；参数3：监视器
        ZooKeeper zk  = new ZooKeeper(connectStr,3000,null);
//        zk.create("/mydir","mycontent".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        String mydirdata=new String(zk.getData("/mydir",false,null));
        System.out.println("mydir内容为:"+mydirdata);


        //修改节点数据。参数1：节点路径名；参数2：节点修改后的数据
        zk.setData("/mydir","modifynodedata".getBytes(),-1);
        String mydirdata1=new String(zk.getData("/mydir",false,null));
        System.out.println("修改节点mydir后的内容为:"+mydirdata1);

        //删除目录节点
        zk.delete("/mydir",-1);

        //关闭zk连接
        zk.close();

    }
}
