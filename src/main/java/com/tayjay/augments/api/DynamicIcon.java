package com.tayjay.augments.api;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.Sys;

import javax.imageio.ImageIO;
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
