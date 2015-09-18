package utils;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
 
public class ImagePanel extends JPanel
{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
    private Image scaledCache;
 
    public ImagePanel()
    {
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }
 
    public void setImage (Image image)
    {
        this.image = image;
        scaledCache = null;
        repaint();
    }
 
    private Image getScaled()
    {
        int iw = image.getWidth(this);
        int ih = image.getHeight(this);
        int pw = getWidth();
        int ph = getHeight();
        double scale;
        if (1.0 * pw / iw < 1.0 * ph / ih)
        {
            scale = 1.0 * pw / iw;
        }
        else
        {
            scale = 1.0 * ph / ih;
        }
        int scaledw = (int) (iw * scale);
        int scaledh = (int) (ih * scale);
        if (scaledCache != null)
        {
            if (scaledCache.getWidth(this) == scaledw &&
                    scaledCache.getHeight(this) == scaledh)
            {
                return scaledCache;
            }
        }
        scaledCache = image.getScaledInstance(scaledw, scaledh, Image.SCALE_DEFAULT);
        return scaledCache;
    }
 
    @Override
    public void paintComponent(Graphics g)
    {
        if (g != null)
        {
            Graphics scratch = g.create();
            scratch.setColor(getBackground());
            scratch.fillRect(0, 0, getWidth(), getHeight());
            if (image != null)
            {
                Image scaled = getScaled();
                scratch.drawImage(scaled, getWidth() / 2 - scaled.getWidth(this) / 2,
                        getHeight() / 2 - scaled.getHeight(this) / 2, this);
            }
        }
    }
}

