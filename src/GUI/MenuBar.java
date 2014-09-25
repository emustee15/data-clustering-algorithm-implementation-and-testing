package GUI;

import java.util.Hashtable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuBar
{
	private Shell shell;
	private Menu menubar;
	private Hashtable<String, MenuItem> menuItems;
	private Hashtable<String, Menu> topLevelMenus;

	MenuBar(Shell shell)
	{
		this.shell = shell;
		menubar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menubar);
		menuItems = new Hashtable<String, MenuItem>();
		topLevelMenus = new Hashtable<String, Menu>();
	}

	public void addTopLevelMenu(String name)
	{
		MenuItem cascadeMenu = new MenuItem(menubar, SWT.CASCADE);
		cascadeMenu.setText("&" + name);
		Menu newTopLevelMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeMenu.setMenu(newTopLevelMenu);
		topLevelMenus.put(name, newTopLevelMenu);
	}

	public void addMenuItem(String topMenuName, String name)
	{
		Menu topLevelMenu = topLevelMenus.get(topMenuName);
		MenuItem newItem;
		newItem = new MenuItem(topLevelMenu, SWT.PUSH);

		newItem.setText("&" + name);

		menuItems.put(name, newItem);

	}

	public void addCheckBoxItem(String topMenuName, String name, boolean defaultState)
	{
		Menu topLevelMenu = topLevelMenus.get(topMenuName);
		MenuItem newItem;

		newItem = new MenuItem(topLevelMenu, SWT.CHECK);

		newItem.setText("&" + name);
		newItem.setSelection(defaultState);
		menuItems.put(name, newItem);
	}

	public void addSelectionListener(SelectionListener e, String name)
	{
		MenuItem item = menuItems.get(name);
		item.addSelectionListener(e);
	}

	public boolean isMenuItemChecked(String name)
	{
		return menuItems.get(name).getSelection();
	}
	
	public MenuItem getItem(String name)
	{
		return menuItems.get(name);
	}
	
	public void addSeperator(String topMenuName)
	{
		Menu topLevelMenu = topLevelMenus.get(topMenuName);
		MenuItem seperator = new MenuItem(topLevelMenu, SWT.SEPARATOR);
	}

}
