package GUI;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Main
{
	public static void main(String args[])
	{
		String os = "", arch = "";

		if (System.getProperty("os.name").contains("win"))
		{
			os = "win";
		}
		else if (System.getProperty("os.name").contains("mac"))
		{
			os = "mac";
		}

		if (System.getProperty("os.arch").contains("64"))
		{
			arch = "64";
		}
		else
		{
			arch = "32";
		}

		String fileName = "lib/swt_" + os + "_x" + arch + ".jar";
		File jarFile = new File(fileName);

		URL url;
		try
		{
			url = jarFile.toURI().toURL();
			URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class<?> urlClass = URLClassLoader.class;
			Method method = urlClass.getDeclaredMethod("addURL", new Class<?>[] { URL.class });
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[] { url });
		}
		catch (MalformedURLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("BAD THINGS HAPPEN");
		}
	
		//DemoWindow window = new DemoWindow();
		//window.main(null);
	}
}
