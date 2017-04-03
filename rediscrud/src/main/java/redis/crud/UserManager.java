package redis.crud;


import java.util.HashMap;
import java.util.Map;

public class UserManager
{
    public static void main( String[] args )
    {
       UserCrud userCrud = new UserCrud();

       /*   ADD USER  */

       String userId = "user:1";
        Map<String,String> map = new HashMap<String, String>();
        map.put("name", "Tatiana");
        map.put("gender","F");
        map.put("age","28");
        map.put("country","UA");

        userCrud.createUserHash(userId,map);


        /*   GET USER  */
        Map<String,String> resultMap=userCrud.getAllUserFields(userId);
        System.out.println("user info for user '"+userId+"'");
        if (resultMap != null){
        for (String keyMap:resultMap.keySet()){
            System.out.println(keyMap+" "+resultMap.get(keyMap));
        }}
        else {
            System.out.println("User not found");
        }


          /*   GET ONE OF USER'S FIELDS  */
        userId = "user:1";
        String field ="name";
        System.out.println("Get name of user '"+userId+"'");
        String result=userCrud.getUserField(userId,field);
        if (result != null){
            System.out.println("User " + field +" is "+result);
        }
        else{System.out.println("User not found");
        }

          /*   UPDATE USER FIELD */
        userId = "user:1";
        field = "gender";
        String value="female";
        boolean updateResult= userCrud.updateUser(userId,field,value);
        if (updateResult){
            System.out.println("The field '"+field+"'"+"of user '"+userId+"' was updaeted" );
        }
        else {System.out.println("User not found");}
          /*   get user  */
        Map<String,String> resultUpdateMap=userCrud.getAllUserFields(userId);
        System.out.println("user info for user '"+userId+"'");
        for (String keyMap:resultUpdateMap.keySet()){
            System.out.println(keyMap+" "+resultUpdateMap.get(keyMap));
        }

          /*   REMOVE USER  */
        /*userId = "user:1";
        boolean delResult = userCrud.removeUser(userId);
        if (delResult){
            System.out.println("User '"+userId+"'"+"was deleted");
        }
        else{System.out.println("User not found");
        }
         *//*   get user  *//*
        Map<String,String> delResultMap=userCrud.getAllUserFields(userId);
        System.out.println("user info for user '"+userId+"'");
        if (!delResultMap.isEmpty()){
            for (String keyMap:delResultMap.keySet()){
                System.out.println(keyMap+" "+delResultMap.get(keyMap));
            }}
        else {
            System.out.println("User not found");
        }*/
    }
}
