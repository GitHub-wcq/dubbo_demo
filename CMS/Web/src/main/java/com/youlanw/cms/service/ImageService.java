package com.youlanw.cms.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.youlanw.cms.entity.mysql.Image;
import com.youlanw.cms.repository.mysql.ImageMapper;
import com.youlanw.cms.utils.Constants;
import com.youlanw.cms.utils.FtpUtil;
import com.youlanw.cms.utils.ImageUtil;
import com.youlanw.cms.utils.YlwImageUtil;
import com.youlanw.common.utils.DateConvertUtils;
import com.youlanw.common.utils.RandomSeriNoUtils;

/**
 * @author Nicolas.Cai
 * @version 1.0
 * @since 1.0
 */

@Component
@Transactional
public class ImageService {

	private ImageMapper imageMapper;

	private Integer wsize = 0;
	private Integer hsize = 0;
	private Integer smallwsize = 0;
	private Integer smallhsize = 0;

	/**
	 * 查询单个对象
	 */
	public Image getImage(java.lang.Integer id) {
		if (id != null) {
			return imageMapper.getById(id);
		}
		return null;
	}

	/**
	 * 保存单个对象
	 */
	public void save(Image entity) {
		imageMapper.insert(entity);
	}

	/**
	 * 删除对象
	 */
	public void delete(java.lang.Integer id) {
		imageMapper.delete(id);
	}

	/**
	 * 处理图片
	 */
	public Image imageProcessing(String comment, String aliPath, String imgPath, Integer imageType, Integer x,
			Integer y, Integer w, Integer h) {
		Image image = new Image();
		image.setCreatetime(DateConvertUtils.getNow());
		image.setType(imageType);
		image.setComment(comment);
		image.setImgpath(imgPath.replace(Constants.YLW_IMAGE_FILE_URL + "320", ""));
		save(image);
		return image;
	}
	/*---------上传文件服务器---------*/

	/**
	 * 统一上传图片 调用的方法 （上传文件服务器）
	 */
	public Image uploadImage(String savePath, MultipartFile fileData, Integer imageType) {
		savePath = savePath + DateConvertUtils.format(DateConvertUtils.getNow(), "yyyyMM");
		String fileName = RandomSeriNoUtils.genCodeByTime("") + "."
				+ ImageUtil.getExtention(fileData.getOriginalFilename());
		String path = null;
		try {
			if (!fileData.isEmpty()) {
				FtpUtil.uploadFile(Constants.YLW_FTP_URL, Constants.YLW_FTP_PORT, Constants.YLW_FTP_USERNAME,
						Constants.YLW_FTP_PASSWORD, "o/" + savePath, fileName, fileData.getInputStream());
				String originalImage = Constants.YLW_IMAGE_FILE_URL + "o/" + savePath + "/" + fileName;
				System.out.println("originalImage:" + originalImage);
				YlwImageUtil.createThumbByWidthnailAndWatermark(savePath, originalImage);
				path = Constants.TMP_FILE_SAVE_PATH + File.separatorChar + fileName;
				System.out.println("ImageService:line94:path:" + path);
			}
			File localTempFile = new File(path);
			localTempFile.mkdirs();
			fileData.transferTo(localTempFile);
			Image image = new Image();
			image.setCreatetime(DateConvertUtils.getNow());
			image.setType(imageType);
			image.setImgpath(savePath + fileName);
			save(image);
			localTempFile.deleteOnExit();
			return image;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*-----end----*/
	/*---------上传文件服务器---------*/

	/**
	 * 统一上传图片 调用的方法 （上传文件服务器）
	 */
	public String uploadImage(String savePath, MultipartFile fileData) {
		savePath = savePath + DateConvertUtils.format(DateConvertUtils.getNow(), "yyyyMM");
		String fileName = RandomSeriNoUtils.genCodeByTime("") + "." + ImageUtil.getExtention(fileData.getOriginalFilename());
		try {
			if (!fileData.isEmpty()) {
				FtpUtil.uploadFile(Constants.FTP_URL, Constants.FTP_PORT, Constants.FTP_USERNAME,Constants.FTP_PASSWORD, savePath, fileName, fileData.getInputStream());
//				YlwImageUtil.createThumbByWidthnailAndWatermark(savePath,Constants.YLW_IMAGE_FILE_URL + "o/" + savePath + "/" + fileName);
				return Constants.IMAGE_FILE_URL + savePath + "/" +  fileName;
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传到static.life.youlanw.com中去
	 *
	 * @return 可访问的直接路径
	 */
	public String uploadImage2StaticLife(String savePath, MultipartFile fileData) {
		savePath = savePath + (savePath.endsWith("/") ? "" : "/")
				+ DateConvertUtils.format(DateConvertUtils.getNow(), "yyyyMM");
		String fileName = RandomSeriNoUtils.genCodeByTime("") + "."
				+ ImageUtil.getExtention(fileData.getOriginalFilename());
		try {
			if (!fileData.isEmpty()) {
				FtpUtil.uploadFile(Constants.FTP_URL, Constants.FTP_PORT, Constants.FTP_USERNAME,
						Constants.FTP_PASSWORD, "o/" + savePath, fileName, fileData.getInputStream());
				ImageUtil.createThumbByWidthnailAndWatermark(savePath,
						Constants.IMAGE_FILE_URL + "o/" + savePath + "/" + fileName);
				return savePath + "/" + fileName;
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*-----end----*/

	/**
	 * 文章新增时 上传图片 返回图片路径
	 */
	public EditUploadJson editorUploadImage(String savePath, MultipartFile fileData) {
		try {
			savePath = savePath + DateConvertUtils.format(DateConvertUtils.getNow(), "yyyyMM");
			String fileName = RandomSeriNoUtils.genCodeByTime("") + "."
					+ ImageUtil.getExtention(fileData.getOriginalFilename());
			if (!fileData.isEmpty()) {
				FtpUtil.uploadFile(Constants.YLW_FTP_URL, Constants.YLW_FTP_PORT, Constants.YLW_FTP_USERNAME,
						Constants.YLW_FTP_PASSWORD, "o/" + savePath, fileName, fileData.getInputStream());
				String originalImage = Constants.YLW_IMAGE_FILE_URL + "o/" + savePath + "/" + fileName;
				System.out.println("imageService:line:154:originalImage:" + originalImage);
				YlwImageUtil.createThumbByWidthnailAndWatermark(savePath, originalImage);
				EditUploadJson json = new EditUploadJson(0,
						Constants.YLW_IMAGE_FILE_URL + "1080/" + savePath + "/" + fileName, "success");
				return json;
			} else {
				EditUploadJson json = new EditUploadJson(1, "", "空文件上传");
				return json;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public class EditUploadJson implements Serializable {

		private static final long serialVersionUID = 1L;

		private Integer error;
		private String url;
		private String message;

		public EditUploadJson(Integer error, String url, String message) {
			super();
			this.error = error;
			this.url = url;
			this.message = message;
		}

		public Integer getError() {
			return error;
		}

		public void setError(Integer error) {
			this.error = error;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

	@Autowired
	public void setImageMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}
}