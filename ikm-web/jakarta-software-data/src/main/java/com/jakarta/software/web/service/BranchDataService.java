package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.BranchDataParamVO;
import com.jakarta.software.web.entity.BranchData;
import com.jakarta.software.web.mapper.BranchDataMapper;

@Service
public class BranchDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BranchDataService.class);
	@Autowired
	private BranchDataMapper branchDataMapper;

	@Autowired
	private BizMessageService messageService;

	@Autowired
	private UserActivityService userActivityService;

	public List<BranchData> selectBranchData() {
		List<BranchData> listBranchData = branchDataMapper.selectBranchData();
		if (listBranchData == null) {
			listBranchData = new ArrayList<BranchData>();
		}
		return listBranchData;
	}

	@Transactional(rollbackFor = Exception.class)
	public void insertBranchData(BranchData branchData) {
		try {
			branchDataMapper.insertBranchData(branchData);
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateBranchData(BranchData branchData) {
		try {
			branchDataMapper.updateBranchData(branchData);
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
		}
	}

	public List<BranchData> selectBranchDataByParam(BranchDataParamVO branchDataParamVO) {
		List<BranchData> listBranchData = branchDataMapper.selectBranchDataByParam(branchDataParamVO);
		if (listBranchData == null) {
			listBranchData = new ArrayList<BranchData>();
		}
		return listBranchData;
	}

	public int countBranchDataByParam(BranchDataParamVO branchDataParamVO) {
		try {
			int count = branchDataMapper.countBranchDataByParam(branchDataParamVO);
			return count;
		} catch (Exception e) {
			return 0;
		}
	}

	public BranchData selectBranchDataById(int id) {
		BranchData branchData = branchDataMapper.selectBranchDataById(id);
		if (branchData == null) {
			branchData = new BranchData();
		}
		return branchData;
	}

	@Transactional(rollbackFor = Exception.class)
	public WebResultVO insertOrUpdateBranchData(BranchData branchData, UserDataLoginVO loginVO, Locale language)
			throws MmbsWebException {
		WebResultVO wrv = new WebResultVO();
		BranchData oriBranchData = new BranchData();
		Date now = new Date();
		branchData.setCreatedBy(loginVO.getId());
		branchData.setCreatedOn(now);
		branchData.setUpdatedBy(loginVO.getId());
		branchData.setUpdatedOn(now);
		branchData.setBranchStatus(WebConstants.STATUS_ACTIVE);
		LOGGER.debug("validate properties branch data!");
		if (StringUtils.isEmpty(branchData.getBranchCode())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { messageService.getMessageFor("l.branchCode") });
		}
		if (StringUtils.isEmpty(branchData.getBranchName())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { messageService.getMessageFor("l.branchName") });
		}
		if (branchData.getId() == 0) {

			int checkDuplicate = branchDataMapper.checkDuplicateBranchByCode(branchData.getBranchCode());
			if (checkDuplicate > 0) {
				throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,
						new String[] { messageService.getMessageFor("l.branchCode") });
			}

			LOGGER.info("Insert Branch Data: {}, user: {}", branchData, loginVO.getUserName());
			try {
				branchDataMapper.insertBranchData(branchData);
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",
						new String[] { messageService.getMessageFor("l.branchData", language), 
							messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);

			} catch (Exception e) {
				LOGGER.warn("Gagal update branch data :" + e);
				throw new MmbsWebException(e);
			}

			/** SET TO USER ACTIVITY **/
			try {
				Collection<String> excludes = new ArrayList<String>();
				excludes.add("createdOn");
				excludes.add("createdBy");
				excludes.add("updatedOn");
				excludes.add("updatedBy");

				userActivityService.generateHistoryActivity(excludes, oriBranchData, branchData, loginVO.getId(),
						WebConstants.ACT_MODULE_BRANCH_DATA, WebConstants.ACT_TYPE_INSERT,
						WebConstants.ACT_TABLE_BRANCH_DATA, loginVO.getId());
			} catch (Exception e) {
				LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
			}
		} else {
			oriBranchData = branchDataMapper.selectBranchDataById(branchData.getId());
			if (!oriBranchData.getBranchCode().equals(branchData.getBranchCode())) {
				int checkDuplicate = branchDataMapper.checkDuplicateBranchByCode(branchData.getBranchCode());
				if (checkDuplicate > 0) {
					throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,
							new String[] { messageService.getMessageFor("l.branchCode") });
				}
			}
			LOGGER.info("Update Branch Data: {}, user: {}", branchData, loginVO.getUserName());
			try {
				branchDataMapper.updateBranchData(branchData);
			} catch (Exception e) {
				LOGGER.warn("Gagal update user data :" + e);
				throw new MmbsWebException(e);
			}
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setMessage(messageService.getMessageFor("rm.generalMessage",
					new String[] { messageService.getMessageFor("l.branchData", language), 
						messageService.getMessageFor("l.updated", language)}, language));
			wrv.setType(WebConstants.TYPE_UPDATE);
			wrv.setPath(WebConstants.PATH_UPDATE_BRANCH_DATA);

			/** SET TO USER ACTIVITY **/
			try {
				Collection<String> excludes = new ArrayList<String>();
				excludes.add("createdOn");
				excludes.add("createdBy");
				excludes.add("updatedOn");
				excludes.add("updatedBy");

				userActivityService.generateHistoryActivity(excludes, oriBranchData, branchData, loginVO.getId(),
						WebConstants.ACT_MODULE_BRANCH_DATA, WebConstants.ACT_TYPE_UPDATE,
						WebConstants.ACT_TABLE_BRANCH_DATA, loginVO.getId());
			} catch (Exception e) {
				LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
			}

		}
		/** SET TO USER ACTIVITY **/
		return wrv;
	}

}