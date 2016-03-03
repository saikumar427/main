<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<style>
 <!--
       #menulinks a:hover{
        text-decoration: none;
       }
-->
</style>
<s:set name="actionName" value="menuMap[#attr['currentaction']]"/>
<div style="height:18px"></div>
<div id='menulinks'>
                <span  class="treehead">Manage</span>
                <a href="CommunityManage?groupId=${groupId}" class="<s:if test="%{#actionName=='snapshot'}" >childactive</s:if><s:else>childtreemenu</s:else>">Summary</a> 
                <!-- <a href="#" class="<s:if test="%{#actionName=='Members'}" >activemenu</s:if><s:else>treehead</s:else>">Members</a> -->
                 <a href="CommunityManage!showManageMembers?groupId=${groupId}" class="<s:if test="%{#actionName=='managemembers'}" >childactive</s:if><s:else>childtreemenu</s:else>">Members</a>
                <a href="membershiptypes?groupId=${groupId}" class="<s:if test="%{#actionName=='membershiptypes'}" >childactive</s:if><s:else>childtreemenu</s:else>">Membership Types</a>
                <a href="CommunityManage!showTermCond?groupId=${groupId}" class="<s:if test="%{#actionName=='termsandcond'}" >childactive</s:if><s:else>childtreemenu</s:else>">Terms &amp; Conditions</a>
                <a href="ManageRegistrationForm?groupId=${groupId}" class="<s:if test="%{#actionName=='manageregistrationform'}" >childactive</s:if><s:else>childtreemenu</s:else>">Registration Form</a>
                <a href="showdiscounts?groupId=${groupId}" class="<s:if test="%{#actionName=='managediscounts'}" >childactive</s:if><s:else>childtreemenu</s:else>">Discount Codes</a>
                <a href="IntegrationLinks?groupId=${groupId}" class="<s:if test="%{#actionName=='IntegrationLinks'}" >childactive</s:if><s:else>childtreemenu</s:else>">Integration Links</a>
                <a href="AddCommunityMember?groupId=${groupId}" class="<s:if test="%{#actionName=='AddCommunityMember'}" >childactive</s:if><s:else>childtreemenu</s:else>">Add Member</a>
                <span  class="treehead">Reports</span>
                <a href="CommunityManage!showMembershipSignup?groupId=${groupId}" class="<s:if test="%{#actionName=='membershipsignupreport'}" >childactive</s:if><s:else>childtreemenu</s:else>">Membership Signups</a>
                <a href="CommunityManage!showMembershipRenewal?groupId=${groupId}" class="<s:if test="%{#actionName=='membershiprenewalreport'}" >childactive</s:if><s:else>childtreemenu</s:else>">Membership Renewals</a>
                <a  class="childtreemenu">&nbsp;</a>
                

       </div>        