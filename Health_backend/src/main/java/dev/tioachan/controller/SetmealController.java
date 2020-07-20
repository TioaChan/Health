package dev.tioachan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.domain.CheckGroup;
import dev.tioachan.domain.Setmeal;
import dev.tioachan.entity.PageResult;
import dev.tioachan.entity.QueryPageBean;
import dev.tioachan.entity.Result;
import dev.tioachan.service.SetmealService;
import dev.tioachan.util.QiniuUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
@CrossOrigin("*")
public class SetmealController {

	@Reference
	private SetmealService setmealService;

	@RequestMapping("/findPage")
	public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
		PageResult pageResult = setmealService.pageQuery(queryPageBean);
		return pageResult;
	}

	@RequestMapping("/uploadSetmealImg")
	public Result uploadSetmealImg(@RequestBody MultipartFile imgFile){
		String originalFilename = imgFile.getOriginalFilename();//原始文件名
		String extention = originalFilename.substring(originalFilename.lastIndexOf("."));//文件扩展名
		String fileName = UUID.randomUUID().toString() + extention;//新文件名

		try {
			//将文件上传到七牛云服务器
			QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
		}


		return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
	}

	@RequestMapping("/add")
	public Result add(@RequestBody Setmeal tempformData, Integer[] checkgroupIds){
		try {
			setmealService.addCheckGroup(tempformData,checkgroupIds);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
		}
		return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
	}
}
