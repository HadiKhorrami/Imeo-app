package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Membership;

import java.util.List;

public class MemberShipLocalServiceUtil {

    private MemberShipLocalService membershipLocalService;

    public MemberShipLocalServiceUtil(Context context) {
        membershipLocalService = new MemberShipLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Membership insertMemberShip(Membership user) {
        return membershipLocalService.createMemberShip(user);
    }

    public Membership updateMemberShip(Membership membership) {
        return membershipLocalService.updateMemberShip(membership);
    }

    public Membership getMemberShipById(long membershipId) {
        return membershipLocalService.getMemberShipById(membershipId);
    }

    public List<Membership> getMemberShipByCode(String membershipcode,long membershipCode) {
        return membershipLocalService.getMemberShipByCode(membershipcode,membershipCode);
    }

    public boolean deleteMemberShip(long userId) {
     return membershipLocalService.deleteMemberShip(userId);
   }

}
