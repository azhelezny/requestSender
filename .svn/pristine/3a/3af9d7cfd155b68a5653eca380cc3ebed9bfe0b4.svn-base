package utils;

import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
 
public class ImageFileChooser extends JFileChooser {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 8927630248951105879L;

	public ImageFileChooser()
    {
        final ImagePanel preview = new ImagePanel();
        preview.setPreferredSize(new Dimension (150, 150));
        setAccessory(preview);
        addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e)
            {
                String propertyName = e.getPropertyName();
                if (propertyName.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY))
                {
                    File selection = (File) e.getNewValue();
                    String name;
                    if (selection == null)
                    {
                        return;
                    }
                    else
                    {
                        name = selection.getAbsolutePath();
                    }
                    ImageIcon icon = new ImageIcon(name);
                    Image newImage = icon.getImage();
                    preview.setImage (newImage);
                }
            }
        });
    }
}