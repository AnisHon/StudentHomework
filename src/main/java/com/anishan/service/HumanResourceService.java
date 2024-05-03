package com.anishan.service;

import com.anishan.entity.Account;
import com.anishan.entity.RestfulEntity;
import com.anishan.exception.ConflictExcption;

public interface HumanResourceService<T> {

    boolean addPersonnel(Account account, T personnel, Object ...objects) throws ConflictExcption;

    default String addPersonnelControllerTool(Account account, T t, Object ...objects) {
        try {
            boolean b = this.addPersonnel(account, t, objects);
            if (b) {
                return RestfulEntity.successMessage("success", "").toJson();
            }
        } catch (ConflictExcption e) {
            return RestfulEntity.failMessage(409, e.getMessage()).toJson();
        }

        return RestfulEntity.failMessage(400, "添加失败，未知错误").toJson();
    }

}
