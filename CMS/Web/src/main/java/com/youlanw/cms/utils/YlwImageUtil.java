package com.youlanw.cms.utils;


import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 * 批量生成图片，图片宽度>=500加水印
 * 
 * @author Administrator
 *
 */
public class YlwImageUtil {
	private static Integer noSupport = 0;
	private static Integer support = 0;
	private static Integer iconCount = 0;

	/**
	 * 生成缩略图，和水印图片
	 * L 大 M 中 S 小
	 */

	public static void createThumbByWidthnailAndWatermark(String ftpPath, String originalImage) {
		Integer sSize = 320;
		Integer mSeze = 720;
		Integer lSize = 1080;
		File file = new File(originalImage);
		String imageName = file.getName();
		String spath = "320/"+ftpPath;//.replace("img", "S/img");
		String mpath = "720/"+ftpPath;//.replace("img", "M/img");
		String lpath = "1080/"+ftpPath;//.replace("img", "L/img");
		int width = getImgWidth(originalImage,file);
		int hight = getImgHight(originalImage,file);
		if (width < 1080) {
			lSize = width;
		}
		if (width < 720) {
			mSeze = width;
		}
		if (width < 320) {
			sSize = width;
		}
		if (width <= 0) {
			width = 0;
		}
		try {
			if (width >= 500) {
				createThumbByWidth(originalImage,imageName, lpath, lSize, (int) (hight * (((float) lSize / (float) width))));
				markImageByIcon(originalImage,imageName, lpath, lSize / 2 - 100, (int) (hight * (((float) lSize / (float) width))) / 2, lpath, 0);
				iconCount++;
			} else {
				createThumbByWidth(originalImage,imageName, lpath, lSize, (int) (hight * (((float) lSize / (float) width))));
			}
			createThumbByWidth(originalImage,imageName, mpath, mSeze, (int) (hight * (((float) mSeze / (float) width))));
			createThumbByWidth(originalImage,imageName, spath, sSize, (int) (hight * (((float) sSize / (float) width))));
		} catch (IOException e) {
			e.printStackTrace();  
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新建文件
	 */
	public static void createFile(String path) {
		File sFile = new File(path.replace("img", "S/img"));
		File mFile = new File(path.replace("img", "M/img"));
		File lFile = new File(path.replace("img", "L/img"));
		File tempFile = new File(path.replace("img", "temp/img"));
		// 创建文件夹
		if (!sFile.exists()) {
			sFile.mkdirs();
		}
		if (!mFile.exists()) {
			mFile.mkdirs();
		}
		if (!lFile.exists()) {
			lFile.mkdirs();
		}
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
	}

	/**
	 * 获取图片宽度
	 * 
	 * @param file
	 *            图片文件
	 * @return 高度
	 */
	public static int getImgWidth(String path,File file) {
		InputStream is = null;
		BufferedImage src = null;
		int ret = -1;
		try {
			URL url = new URL(path);
			is = url.openConnection().getInputStream();  
			src = ImageIO.read(is);  
			ret = src.getWidth(null); // 得到源图高
			is.close();
			support++;
		} catch (Exception e) {
			noSupport++;
//				copyFile(file, new File(file.getPath().replace("img", "L/img")));
//				copyFile(file, new File(file.getPath().replace("img", "S/img")));
//				copyFile(file, new File(file.getPath().replace("img", "M/img")));
			System.out.println(file.getPath() + "不支持压缩");
		}
		return ret;
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	/**
	 * 获取图片高度
	 * 
	 * @param file
	 *            图片文件
	 * @return 高度
	 */
	public static int getImgHight(String path,File file) {
		InputStream is = null;
		BufferedImage src = null;
		int ret = -1;
		try {
			URL url = new URL(path);
			is = url.openConnection().getInputStream();  
			src = ImageIO.read(is);  
			ret = src.getHeight(null); // 得到源图高
			is.close();
		} catch (Exception e) {
			System.out.println(file.getPath() + "不支持压缩");
		}
		return ret;
	}
	/**
	 * 取后缀名
	 * @param fileName
	 * @return
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1);
	}
	
	 /**  
     * 给图片添加水印、可设置水印图片旋转角度  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     * @param degree 水印图片旋转角度  
     */  
    public static void markImageByIcon(String originalImage,String fileName, String srcImgPath,Integer x, Integer y,   
            String targerPath, Integer degree) {   
        try {   
        	URL url = new URL(originalImage);
			InputStream oldIs = url.openConnection().getInputStream();  
			Image srcImg = ImageIO.read(oldIs);  
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),   
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
  
            // 得到画笔对象   
            Graphics2D g = buffImg.createGraphics();   
  
            // 设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
  
            if (null != degree) {   
                // 设置水印旋转   
                g.rotate(Math.toRadians(degree),   
                        (double) buffImg.getWidth() / 2, (double) buffImg   
                                .getHeight() / 2);   
            }   
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度   
            ImageIcon imgIcon = new ImageIcon(Constants.IMAGE_ICON_PATH);   
            // 得到Image对象。   
            Image img = imgIcon.getImage();   
            float alpha = 1f; // 透明度   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,   
                    alpha));   
            // 表示水印图片的位置   
            g.drawImage(img,x,y,null);   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));   
            g.dispose();   
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            // 生成图片   
            ImageIO.write(buffImg, "JPG", os);   
            InputStream is = new ByteArrayInputStream(os.toByteArray());  
            FtpUtil.uploadFile(Constants.YLW_FTP_URL, Constants.YLW_FTP_PORT, Constants.YLW_FTP_USERNAME, Constants.YLW_FTP_PASSWORD, srcImgPath, fileName, is);
        } catch (Exception e) {   
            e.printStackTrace();   
        } 
    }  
    /**
	 * 按比例缩放图片
	 * @param srcImgPath 原图片地址
	 * @param destImgPath 目标图片地址
	 * @param destImgW 目标宽度
	 * @param destImgH 目标高度
	 * @throws IOException 
	 */
	private static void createThumbByWidth (String srcImgPath ,String fileName, String destImgPath, int destImgW , int destImgH) throws IOException{
	    //原图片等比例缩小或放大之后的图片
	    int narrowImgW ;
	    int narrowImgH ;
	    //原图片大小
	    int srcImgW ;
	    int srcImgH ;
		URL url = new URL(srcImgPath);
		InputStream oldIs = url.openConnection().getInputStream();  
        BufferedImage bi = ImageIO.read(oldIs);
        srcImgW = bi.getWidth();
        srcImgH = bi.getHeight();
        narrowImgW = destImgW;
        narrowImgH = ( int) (((float )destImgW / (float)srcImgW)*srcImgH);
        int cutNarrowImgSize = (narrowImgH - destImgH)/2;

        BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH,BufferedImage.TYPE_INT_RGB);
        narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH ), 0, 0, null);

        Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT );
        CropImageFilter cropFilter = new CropImageFilter(0, cutNarrowImgSize, narrowImgW, narrowImgH-cutNarrowImgSize);
        Image img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
        BufferedImage cutTopNarrowImg = new BufferedImage( narrowImgW, narrowImgH-cutNarrowImgSize,BufferedImage. TYPE_INT_RGB);
        cutTopNarrowImg.getGraphics().drawImage(img, 0, 0, null);

        image = cutTopNarrowImg.getScaledInstance(narrowImgW, narrowImgH-cutNarrowImgSize, Image. SCALE_DEFAULT);
        cropFilter = new CropImageFilter(0, 0, narrowImgW, narrowImgH-cutNarrowImgSize*2);
        img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
        BufferedImage cutBottomNarrowImg = new BufferedImage( narrowImgW, narrowImgH-cutNarrowImgSize*2,BufferedImage. TYPE_INT_RGB);
        Graphics g = cutBottomNarrowImg.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 生成图片   
        ImageIO.write(cutBottomNarrowImg, "JPEG", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());  
        FtpUtil.uploadFile(Constants.YLW_FTP_URL, Constants.YLW_FTP_PORT, Constants.YLW_FTP_USERNAME, Constants.YLW_FTP_PASSWORD, destImgPath, fileName, is);
	}
	
	/**
	 * 裁剪图片
	 * @param srcImageFile 源图像地址
	 * @param x 目标切片起点x坐标
	 * @param y 目标切片起点y坐标
	 * @param destWidth 目标切片宽度
	 * @param destHeight 目标切片高度
	 */
	public static void abscut(String srcImageFile, int x, int y, int destWidth, int destHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                
               /* ****************************************
                *  判断原图的宽高和DIV宽高的大小
                *  若srcWidth>DIV宽，则【w=destWidth*srcWidth/420】
                *  若srcWidth<DIV宽，则【w=destWidth】
                *  若srcHeight>DIV高，则【h=destHeight*srcHeight/420】
                *  若srcHeight<DIV高，则【h=destHeight】
                *************************************** */
                int x1 = x*srcWidth/420;
                int y1 = y*srcWidth/420;
                int w = destWidth*srcWidth/420;
                int h = destHeight*srcWidth/420;

                System.out.println("srcWidth= " + srcWidth + "\tsrcHeight= " + srcHeight);
                System.out.println("w= " + w + "\t h= "  + h);
                System.out.println("x1= " + x1 + "\t y1= "  + y1);
                System.out.println("x= " + x + "\t y= "  + y);
                System.out.println("destWidth= " + destWidth + "\t destHeight= "  + destHeight);
                System.out.println("srcWidth/420= " + srcWidth/420 + "\t srcHeight/420= "  + srcHeight/420);
                
                // 改进的想法:是否可用多线程加快切割速度
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                cropFilter = new CropImageFilter(x1, y1, w, h);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(srcImageFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * 按比例缩放图片
	 * @param srcImgPath 原图片地址
	 * @param destImgPath 目标图片地址
	 * @param destImgW 目标宽度
	 * @param destImgH 目标高度
	 * @throws IOException 
	 */
	public static void createThumb(String srcImgPath , String destImgPath, int destImgW , int destImgH) throws IOException{
	    //原图片等比例缩小或放大之后的图片
	    int narrowImgW ;
	    int narrowImgH ;
	    //原图片大小
	    int srcImgW ;
	    int srcImgH ;
	        BufferedImage bi = ImageIO. read(new File(srcImgPath));
	        srcImgW = bi.getWidth();
	        srcImgH = bi.getHeight();
	        // 转换图片尺寸与目标尺寸比较 ， 如果转换图片较小，说明转换图片相对于目标图片来说高较小，需要以高为基准进行缩放。
	        if((float )srcImgW /srcImgH > (float)destImgW / destImgH){
	            narrowImgW = ( int)(((float )destImgH / (float)srcImgH)*srcImgW);
	            narrowImgH = destImgH;
	            //按照原图以高为基准等比例缩放、或放大。这一步高为所需图片的高度，宽度肯定会比目标宽度宽。
	            int cutNarrowImgSize = (narrowImgW - destImgW)/2;
	            BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH,BufferedImage.TYPE_INT_RGB);
	            narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH ), 0, 0, null);
	            //等比例缩放完成后宽度与目标尺寸宽度相比较 ， 将多余宽的部分分为两份 ，左边删除一部分
	            Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT );
	            CropImageFilter cropFilter = new CropImageFilter(cutNarrowImgSize, 0, narrowImgW-cutNarrowImgSize, narrowImgH);
	            Image img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
	            BufferedImage cutLiftNarrowImg = new BufferedImage( narrowImgW-cutNarrowImgSize, narrowImgH,BufferedImage.TYPE_INT_RGB );
	            cutLiftNarrowImg.getGraphics().drawImage(img, 0, 0, null);
	            //右边删除一部分
	            image = cutLiftNarrowImg.getScaledInstance(narrowImgW-cutNarrowImgSize, narrowImgH, Image.SCALE_DEFAULT );
	            cropFilter = new CropImageFilter(0, 0, narrowImgW-cutNarrowImgSize*2, narrowImgH);
	            img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
	            BufferedImage cutRightNarrowImg = new BufferedImage( narrowImgW-cutNarrowImgSize*2, narrowImgH,BufferedImage.TYPE_INT_RGB );
	            Graphics g = cutRightNarrowImg.getGraphics();
	            g.drawImage(img, 0, 0, null); // 绘制截取后的图
	            g.dispose();
	            //输出为文件 最终为所需要的格式
	            ImageIO. write(cutRightNarrowImg, "JPEG", new File(destImgPath));
	        }
	        else{ //以宽度为基准
	            narrowImgW = destImgW;
	            narrowImgH = ( int) (((float )destImgW / (float)srcImgW)*srcImgH);
	            int cutNarrowImgSize = (narrowImgH - destImgH)/2;

	            BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH,BufferedImage.TYPE_INT_RGB);
	            narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH ), 0, 0, null);

	            Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT );
	            CropImageFilter cropFilter = new CropImageFilter(0, cutNarrowImgSize, narrowImgW, narrowImgH-cutNarrowImgSize);
	            Image img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
	            BufferedImage cutTopNarrowImg = new BufferedImage( narrowImgW, narrowImgH-cutNarrowImgSize,BufferedImage. TYPE_INT_RGB);
	            cutTopNarrowImg.getGraphics().drawImage(img, 0, 0, null);

	            image = cutTopNarrowImg.getScaledInstance(narrowImgW, narrowImgH-cutNarrowImgSize, Image. SCALE_DEFAULT);
	            cropFilter = new CropImageFilter(0, 0, narrowImgW, narrowImgH-cutNarrowImgSize*2);
	            img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
	            BufferedImage cutBottomNarrowImg = new BufferedImage( narrowImgW, narrowImgH-cutNarrowImgSize*2,BufferedImage. TYPE_INT_RGB);
	            Graphics g = cutBottomNarrowImg.getGraphics();
	            g.drawImage(img, 0, 0, null);
	            g.dispose();
	            ImageIO.write(cutBottomNarrowImg, "JPEG", new File(destImgPath));
	        }
	}
	
}
