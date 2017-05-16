

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by cs.ucu.edu.ua on 13.05.2017.
 */
public class ParallelDataProcessing {
    public synchronized Task GetInfFromBD(int Id) throws Exception {
        Class.forName("ParallelDataProcessing");
        String server = "localhost\\SOLEXPRESS";//Q?
        int port = 1433;
        String database = "Parallel";
        String Url = "jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + database + ";integratedSecurity=true";
        Connection con = DriverManager.getConnection(Url);

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Data FROM MyDB WHERE ID = " + Id + ";");

        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT IDClient FROM MyDB WHERE ID = " + Id + ";");

        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT IDProject FROM MyDB WHERE ID = " + Id + ";");

        int client=0;
        int project=0;
        String Data = "";

        while(rs1.next()) {
            String c = rs1.getString(1);
            client = Integer.parseInt(c);
        }

        while(rs2.next()) {
            String p = rs2.getString(1);
            project = Integer.parseInt(p);
        }

        while(rs.next()) {
            Data = rs.getString(1);
        }
        Task task = new Task();
        task.setClientID(client);
        task.setProjectID(project);
        task.setData(Data);
        task.setId(Id);
        return task;
    }
    public synchronized Task FindJarAndProcess(final File folder, Task ClientData) throws Exception {
        int ClientId = ClientData.getClientID();
        int ProjectId = ClientData.getProjectID();
        int ID = ClientData.getId();
        String data = ClientData.getData();
        Task task = new Task();
        List<String> JarNames = new ArrayList<String>();

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                FindJarAndProcess(fileEntry, ClientData);
            } else {
                JarNames.add(fileEntry.getName());

            }
        }

        for (String a : JarNames) {
                String pathToJar = "C:/Users/cs.ucu.edu.ua/IdeaProjects/papochka1/" + a;
                JarFile jarFile = new JarFile(pathToJar);
                Enumeration<JarEntry> e = jarFile.entries();

                URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                while (e.hasMoreElements()) {
                        JarEntry je = e.nextElement();
                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
                            continue;
                        }
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace('/', '.');
                        Class c = cl.loadClass(className);
                        Constructor<?> ctor = c.getConstructor();
                        Object object = ctor.newInstance();
                        if (object instanceof Interf) {
                            Interf ic = (Interf) object;
                            Integer client = ic.getClientID();
                            Integer project = ic.getProjectID();
                           // System.out.print("cl"+ClientId+" "+"pr"+ProjectId+" "+data);
                            if (client == ClientId && project == ProjectId && !data.contains("/")){
                                task = ic.DoAndBornTasks(ClientData);

                            }
                            else{
                                task = ic.doTask(ClientData);
                            }

                           // System.out.print("\n");
                        }

                }
        }
        return task;
    }

}
