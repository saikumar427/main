<%@taglib uri="/struts-tags" prefix="s"%>
<s:set name="temp" value="12"/>
<s:iterator status="stat" value="(#temp).{#this}" var="a">
   <!-- grab the index (start with 0 ... ) -->
   <!-- <s:property value="#stat.index" /> -->
 <s:property value="#a" />
   <!-- grab the top of the stack which should be the -->
   <!-- current iteration value (0, 1, ... 5) -->
 <!--  <s:property value="top" />  -->
</s:iterator>

