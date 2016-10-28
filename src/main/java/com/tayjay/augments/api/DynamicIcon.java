package com.tayjay.augments.api;

import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.Sys;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Created by tayjay on 2016-08-21.
 * Create an icon for an item using dynamic code.
 */
public class DynamicIcon
{
    private static final GraphicsConfiguration config =
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();


    public static void makeBodyPartItemPNG(Item item, PartType type,String textureName)
    {

        try
        {
            String name = item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(':')+1);
            makeJSONGeneric("augments",name);
            String dir = item.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            int ind = dir.lastIndexOf("Augments");//todo: MAKE MORE GENERIC
            if(ind >= 0)
                dir = new StringBuilder(dir).substring(1, ind+"Augments".length());
            if(dir==null)
                return;
            File sourceFile = new File(dir+"/assets/augments/textures/models/" + textureName + ".png");
            if(sourceFile==null || !Files.exists(sourceFile.toPath(),LinkOption.NOFOLLOW_LINKS))
                return;
            File destFile = new File(dir+"/assets/augments/textures/items/" + name + ".png");
            if(destFile==null || Files.exists(destFile.toPath(), LinkOption.NOFOLLOW_LINKS))
            {
                System.out.println(name+" already has image, skipping.");
                return;
            }

            BufferedImage src = ImageIO.read(sourceFile);
            BufferedImage dest;
            int x,y,w,h,scale;

            switch (type)
            {
                case HEAD:
                    x=8;
                    y=8;
                    w=8;
                    h=8;
                    break;
                case EYES:
                    x=8;
                    y=8;
                    w=8;
                    h=8;
                    break;
                case TORSO:
                    x=20;
                    y=20;
                    w=8;
                    h=12;
                    break;
                case ARM:
                    x=40;
                    y=20;
                    w=4;
                    h=12;
                    break;
                case LEG:
                    x=4;
                    y=20;
                    w=4;
                    h=12;
                    break;
                default:
                    x=0;
                    y=0;
                    w=0;
                    h=0;
            }
            scale = src.getWidth()/64;
            x*=scale;
            y*=scale;
            w*=scale;
            h*=scale;
            dest = src.getSubimage(x,y,w,h);
            if(dest.getWidth()!=dest.getHeight())
            {
                int newImageWidth = Math.max(dest.getWidth(),dest.getHeight()) ;
                int newImageHeight = Math.max(dest.getWidth(),dest.getHeight()) ;
                int posX = (newImageHeight-dest.getWidth())/2;
                BufferedImage resizedImage = config.createCompatibleImage(newImageWidth,newImageHeight);
                Graphics2D g = resizedImage.createGraphics();
                //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.setColor(Color.white);
                g.fillRect(0,0,newImageWidth,newImageHeight);
                g.drawImage(dest, posX, 0, dest.getWidth() , dest.getHeight() , null);
                g.dispose();

                //resizedImage = (BufferedImage) resizedImage.getScaledInstance(newImageWidth,newImageHeight,Image.SCALE_SMOOTH);

                ImageIO.write(resizedImage, "PNG", destFile);
            }
            else
            {
                ImageIO.write(dest, "PNG", destFile);
            }
            System.out.println("Created new image for item "+name);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * If the specified Item does not have an image file or JSON corrisponding to it, 1 of each will be created for it.
     * @param item      Item to make image and JSON for
     * @param modid     MODID for your mod.
     * @param modname
     */
    public static void createItemIconGeneric(Item item, String modid, String modname)
    {
        makePNGGeneric(modid,modname,item.getUnlocalizedName(),item.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    /**
     * Create a generic PNG file for the item.
     * @param itemName Name of icon for an item
     */
    public static void makePNGGeneric(String modid,String modName,String itemName,String dir)
    {
        //System.out.println(DynamicIcon.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        try
        {
            int ind = dir.lastIndexOf(modName);//todo: MAKE MORE GENERIC
            if(ind >= 0)
                dir = new StringBuilder(dir).substring(1, ind+modName.length());
            File file = new File(dir+"/assets/"+modid+"/textures/items/" + itemName + ".png");
            if(Files.exists(file.toPath(), LinkOption.NOFOLLOW_LINKS))
            {

                System.out.println(itemName+" already has icon. Skipping...");
                return;
            }
            int width = 128, height = 128;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = image.createGraphics();

            Font font = new Font("TimesRoman", Font.BOLD, 20);
            graphics2D.setFont(font);

            FontMetrics fontMetrics = graphics2D.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(itemName);
            int stringHeight = fontMetrics.getAscent();
            graphics2D.setPaint(Color.BLACK);
            graphics2D.drawString(itemName, (width - stringWidth) / 2, height / 2 + stringHeight / 4);


            System.out.println(file.getAbsolutePath());
            ImageIO.write(image, "PNG", file);
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (NullPointerException np)
        {
            System.out.println("NullPointer loading "+itemName);
            np.printStackTrace();
        }
    }

    public static void makeJSONGeneric(String modid, String itemName)
    {
        try
        {
            String currentPath = DynamicIcon.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            int ind = currentPath.lastIndexOf("Augments");//todo: MAKE MORE GENERIC
            if(ind >= 0)
                currentPath = new StringBuilder(currentPath).substring(1, ind+"Augments".length());
            Path from = Paths.get(currentPath+"/assets/"+modid+"/models/item/generic.json");
            Path to = Paths.get(currentPath+"/assets/"+modid+"/models/item/"+itemName+".json");
            if(Files.exists(to,LinkOption.NOFOLLOW_LINKS))
            {
                System.out.println(itemName+" already has JSON. Skipping...");
                return;
            }
            Files.copy(from,to, StandardCopyOption.REPLACE_EXISTING);

            Charset charset = StandardCharsets.UTF_8;

            String content = new String(Files.readAllBytes(to),charset);
            content = content.replaceAll("ICON_NAME",itemName);
            content = content.replaceAll("MODID",modid);
            Files.write(to,content.getBytes(charset));
            System.out.println(to.toFile().getAbsolutePath());

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
