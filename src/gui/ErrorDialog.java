/*
 * You can use this class generate a pop-up dialog that displays an error
 * that can be passed into the constructor.
 */

package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;

import selectionListeners.CloseDialogBehavior;
import selectionListeners.ExitBehavior;
import org.eclipse.swt.custom.CLabel;

public class ErrorDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private String errorMessage;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ErrorDialog(Shell parent, int style) {
		super(parent, style);
		setText("Error");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open(String errorMessage) {
		this.errorMessage = errorMessage;
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(400, 150);
		shell.setText(getText());
		
		Label messageLabel = new Label(shell, SWT.WRAP);
		messageLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		messageLabel.setBounds(59, 10, 306, 56);
		messageLabel.setText(errorMessage);
		
		Button okButton = new Button(shell, SWT.NONE);
		okButton.setBounds(176, 71, 50, 25);
		okButton.setText("OK");
		okButton.addSelectionListener(new CloseDialogBehavior(shell));
		
		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(ErrorDialog.class, "/images/icon_error.png"));
		lblNewLabel.setBounds(10, 10, 43, 56);
		lblNewLabel.setText("");

	}
}
