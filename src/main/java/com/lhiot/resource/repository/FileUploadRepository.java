package com.lhiot.resource.repository;

import com.lhiot.resource.entity.ResourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leon (234239150@qq.com) created in 10:29 18.10.12
 */
public interface FileUploadRepository extends JpaRepository<ResourceFile, Long>, JpaSpecificationExecutor<ResourceFile> {

}
