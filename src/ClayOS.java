import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.mypapyri.clay.ClaySystem;
import com.mypapyri.clay.event.KoboTouchInput;
import com.mypapyri.clay.event.SwingMouseInput;
import com.mypapyri.clay.ui.App;
import com.ramuller.clay.display.Display;
import com.ramuller.clay.display.SwingDisplay;



public class ClayOS extends App implements ActionListener{

	private static final long serialVersionUID = 3343633470257570334L;
	

	public ClayOS() {
		super();
		JPanel panel = this;
		panel.setBackground(Color.red);
		panel.setLayout(new BorderLayout());
		JButton button1,button2,button3,button4,button5;
		button1=new JButton("North");
		button2=new JButton("South");
		button3=new JButton("East");
		button4=new JButton("West");
		button5=new JButton("Center");

		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);

		panel.add(button1,BorderLayout.NORTH);
		panel.add(button2,BorderLayout.SOUTH);
		panel.add(button3,BorderLayout.EAST);
		panel.add(button4,BorderLayout.WEST);
		panel.add(button5,BorderLayout.CENTER);
		panel.getLayout().layoutContainer(panel);
		
		reflow();
	}
	

	public static void main(String[] args) {
		
		EInkFB fb = null;
		Display display = null;
		if (System.getProperty("user.home").length() < 4) {
			try {
				fb = new EInkFB("/dev/fb0");
				display = new EInkDisplay(fb);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			catch (UnsatisfiedLinkError e) {
				e.printStackTrace();
				// System.out.println("Anscheinend kein EInk-Display vorhanden, deshalb Wechsel zu einem Swing-Display!");

			}

			App.setDisplay(display);
			ClayOS su = new ClayOS();
			ClaySystem.setActiveApp(su);

			KoboTouchInput kti = null;
			try {
				kti = new KoboTouchInput("/dev/input/event1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			kti.addListener(su);
			kti.start();
			
			try{
				while(true){
					su.repaint();
					Thread.sleep(1000);
				}
			}catch(Exception e){
				
			}

		}

		if (display == null) {
			display = new SwingDisplay(600, 800);

			while(display==null){
				System.out.print('.');
			}

			App.setDisplay(display);
			ClayOS su = new ClayOS();
			ClaySystem.setActiveApp(su);
			
			while(su.getDisplay()==null){
				System.out.print('.');
				su.repaint();
			}


			SwingMouseInput smi = new SwingMouseInput();
			((SwingDisplay)display).icon.addMouseListener(smi);
			smi.addListener(su);
			
			/*
			 * SwingMouseInput smi = null; SwingKeyInput ski = null; smi = new
			 * SwingMouseInput(); ski = new SwingKeyInput();
			 * ((SwingDisplay)mainDisplay).frame.addMouseListener(smi);
			 * ((SwingDisplay)mainDisplay).frame.addMouseMotionListener(smi);
			 * ((SwingDisplay)mainDisplay).frame.addKeyListener(ski);
			 * smi.addEventListener(appMan); ski.addEventListener(appMan);
			 */

		}

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(((JButton)e.getSource()).getText());
		((JButton)e.getSource()).setText("Poke! "+(System.currentTimeMillis()-ClaySystem.getLaunchTime())/1000);
		reflow();
	}
}
