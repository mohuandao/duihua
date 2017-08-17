package com.newcoder.duihua.controller;

/**
 * Created by wangdong on 2017/8/2.
 */
//@Controller
//public class IndexController {
//    @RequestMapping(path = {"/","index"})
//    @ResponseBody
//    public String index() {
//        return "hello,everyone";
//    }
//
//    @RequestMapping(path = "/profile/{groupId}/{userId}")
//    @ResponseBody
//    public String profile(@PathVariable("userId") int userId,
//                          @PathVariable("groupId") String groupId,
//                          @RequestParam(value = "type",defaultValue = "1") int type,
//                          @RequestParam(value = "key",defaultValue = "zz") String key) {
//        return "hello,everyone" +groupId+"/"+userId +type+key;
//    }
//
//    @RequestMapping(path = {"/html"},method = RequestMethod.GET)
//    public String template(Model model) {
//        model.addAttribute("value1","2333");
//        model.addAttribute("value2","大家好");
//        List<String> colors = Arrays.asList(new String[]{"BLUE","RED","GREEN"});
//        model.addAttribute("colors",colors);
//        return "home";
//    }
//}
