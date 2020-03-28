/*
 * package com.trax.ems.elasticSearch.handler;
 * 
 * import java.util.function.Function;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.stereotype.Component;
 * 
 * import com.trax.ems.elasticSearch.bean.ProfileDocument; import
 * com.trax.ems.elasticSearch.bean.RequestDTO; import
 * com.trax.ems.elasticSearch.controller.ProfileController;
 * 
 * 
 * Implement Function and override apply to provide lambda access. According to
 * action in lambda i/p it work.
 * 
 * 
 * @Component public class ElasticSearch implements Function<RequestDTO, String>
 * {
 * 
 * 
 * @Autowired ProfileController profileController;
 * 
 * @Override public String apply(RequestDTO requestDTO) {
 * 
 * String action = requestDTO.getAction(); System.out.println("äction : " +
 * action);
 * 
 * ProfileDocument profileDoc = requestDTO.getProfileDocument(); ResponseEntity
 * responseEntity = null; try { if (action.equalsIgnoreCase("Post")) {
 * responseEntity = profileController.createProfile(profileDoc);
 * System.out.println("Elsatic Search response Entity --" + responseEntity); }
 * else if (action.equalsIgnoreCase("getById")) { String id =
 * profileDoc.getId();
 * System.out.println("Elastic Search apply findById id --- " + id);
 * profileController.findById(id); } else if
 * (action.equalsIgnoreCase("updateById")) {
 * System.out.println("Elastic Search apply updateProfile profileDocument --- "
 * + profileDoc); profileController.updateProfile(profileDoc); } else if
 * (action.equalsIgnoreCase("deleteById")) { String id = profileDoc.getId();
 * System.out.println("Elastic Search apply deleteById id --- " + id);
 * profileController.deleteProfileDocument(id); } else if
 * (action.equalsIgnoreCase("searchAll")) {
 * System.out.println("Elastic Search apply search All ---- " + action);
 * profileController.findAll(); }
 * 
 * } catch (Exception e) { e.printStackTrace(); }
 * 
 * return "Action Completed."; } }
 */