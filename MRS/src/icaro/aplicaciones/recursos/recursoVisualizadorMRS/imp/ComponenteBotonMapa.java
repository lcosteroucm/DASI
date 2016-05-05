package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import icaro.aplicaciones.MRS.informacion.VocabularioMRS;

public class ComponenteBotonMapa extends JButton {
	
	//ARTE
	private static String rutaArteEscenario = VocabularioMRS.RutaArte+"/";
	private static String rutaArteBG = VocabularioMRS.RutaArteBG+"/";
	private static String rutaMin ="miner.png";
	private static String rutaRob ="robot.png";
	private static String rutaPie ="piedra.png";
	
	private ImageIcon bg;
	private CombineIcon ci;
	
	public ComponenteBotonMapa(int type) {
		super();
		bg = new ImageIcon(getIcono(type));
		ci = new CombineIcon("background",bg);
		setIcon(ci);
		
		
		setMargin(new Insets(0, 0, 0, 0));
		setBorder(new EmptyBorder(0,0,0,0));

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ComponenteBotonMapa btn = (ComponenteBotonMapa) e.getComponent();
				Dimension size = btn.getSize();
				btn.rescale(size.width,size.height);
			}

		});
	}
	
	private String getIcono(int type){
		if(type >=0 && type <=16)
			return rutaArteBG+type+".png";
		else
			return rutaArteEscenario+"error.png";
	}
	public void removeElement(String id){
		ci.removeIcon(id);
		repaint();
	}
	public void addIcon(String id,ImageIcon ic, Dimension dimension){
		Image img = ic.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_FAST);
		ci.addIcon(id, new ImageIcon(img));
		repaint();
	}
	
	public void addElement(String id,String Type){
		switch(Type){
		case "Miner":
			ci.addIcon(id, new ImageIcon(rutaArteEscenario+rutaMin));
			break;
		case "Robot":
			ci.addIcon(id, new ImageIcon(rutaArteEscenario+rutaRob));
			break;
		case "Escombro":
		case "NOPiedra":
			ci.addIcon("PIEDRA", new ImageIcon(rutaArteEscenario+"NO"+rutaPie));
			break;
		case "Piedra":
			ci.removeIcon("PIEDRA");
			ci.addIcon("PIEDRA", new ImageIcon(rutaArteEscenario+rutaPie));
			break;
		}
		repaint();
	}
	public void rescale(int w, int h){
		ci.rescale(w,h);
		repaint();
	}
	
	public void dibujaAgente(String idAgente,String tipo) {
		addElement(idAgente,tipo);
	}
	public void dibujaAgente2(String idAgente) {
		addElement(idAgente,"Miner");
	}
	public void eliminaAgente(String idAgente) {
		removeElement(idAgente);
	}
	
	public class CombineIcon implements Icon {
	    private Vector<ImageIcon> list;
	    private Vector<String> listId;
	    private Dimension lastSize;

	    public CombineIcon(){
	    	list = new Vector<ImageIcon>();
	    	listId = new Vector<String>();
	    	lastSize = new Dimension(10,10);
	    }
	    public CombineIcon(String id, ImageIcon ic) {
	    	this();
	    	addIcon(id,ic);
	    }
	    
	    public void rescale(int wd, int hg){
	    	Image im;
	    	int w=wd;
	    	if ( list.size()>1){
	    		w = wd/list.size()-1;
	    	}
	    	im = list.get(0).getImage().getScaledInstance(wd,hg,Image.SCALE_FAST);
    		list.set(0, new ImageIcon(im));

	    	for(int i = 1; i < list.size(); i++){
	    		im = list.get(i).getImage().getScaledInstance(w,hg,Image.SCALE_FAST);
	    		list.set(i, new ImageIcon(im));
	    	}
	    	lastSize = new Dimension(wd,hg);
	    	
	    }
	    
	    public void addIcon(String id,ImageIcon ic){
	    	Image im = ic.getImage().getScaledInstance(lastSize.width,lastSize.height,Image.SCALE_FAST);
	    	list.addElement(new ImageIcon(im));
	    	listId.addElement(id);
	    }
	    
	    public void addIcon(int idx, String id,ImageIcon ic){
	    	Image im = ic.getImage().getScaledInstance(lastSize.width,lastSize.height,Image.SCALE_FAST);
	    	list.add(idx,new ImageIcon(im));
	    	listId.add(idx,id);
	    }
	    
	    public void removeIcon(int idx){
	    	list.remove(idx);
	    	listId.remove(idx);
	    }
	    
	    public void removeIcon(String id){
	    	Iterator<String> it = listId.iterator();
	    	int i = 0;
	    	while(it.hasNext()){
	    		String s = it.next();
	    		if(s.equals(id)){
	    			removeIcon(i);
	    			break;
	    		}
	    		i++;
	    	}
	    }

	    public int getIconHeight() {
	    	int max = 0;
	    	int h;
	    	for(int i = 0; i < list.size(); i++){
	    		h = list.get(i).getIconHeight();
	    		if(h > max){
	    			max = h;
	    		}
	    	}
	        return max;
	    }

	    public int getIconWidth() {
	    	int max = 0;
	    	int h;
	    	for(int i = 0; i < list.size(); i++){
	    		h = list.get(i).getIconWidth();
	    		if(h > max){
	    			max = h;
	    		}
	    	}
	        return max;
	    }
	    
	    @Override
	    public void paintIcon(Component c, Graphics g, int x, int y) {
	    	list.get(0).paintIcon(c,g,x,y);
	    	int inc = 0;
	    	for(int i = 1; i < list.size(); i++){
	    		list.get(i).paintIcon(c,g,inc,y);
	    		inc += 10;
	    	}
	    }

		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6020843624647630830L;

	
}