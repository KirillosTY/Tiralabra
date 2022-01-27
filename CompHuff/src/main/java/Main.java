import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;

import javax.naming.BinaryRefAddr;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {


    count("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse egestas lorem sodales sem molestie, a varius tortor imperdiet. Na" +
            "m quis lacus eu magna blandit sagittis at sit amet turpis. Morbi ac pretium leo. Pellentesque habitant morbi tristique senectus et netus et malesu" +
            "ada fames ac turpis egestas. In ut enim quis nulla bibendum aliquam sed at mi. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed at metus eu" +
            " nibh blandit consectetur. Cras ut odio sodales, volutpat nisl sit amet, egestas ex. Praesent posuere mi purus, nec dignissim odio ultricies ac. Int" +
            "eger scelerisque ac nunc quis lobortis. Aliquam at suscipit nisl, quis accumsan est. Sed at mattis purus. Cras malesuada nisl sit amet tincidunt bla" +
            "dit. Nunc commodo id diam ut feugiat. Etiam imperdiet metus non purus laoreet, non egestas est auctor."+
            "Nunc varius quis dolor fringilla egestas. Sed eget libero luctus, accumsan lectus non, scelerisque arcu. Etiam tristique ac nibh et " +
            "tempor. Praesent pellentesque ligula et dui blandit maximus. Donec condimentum porta nisi ac tempor. Praesent eleifend tellus mauris, at auctor sem e" +
            "leifend in. Ut vulputate, purus vel maximus porta, est justo sagittis orci, commodo commodo ipsum lacus vel nibh. Curabitur sed metus dapibus, posu" +
            "ere nunc at, fringilla lorem. Duis a sapien neque. Aenean consequat sagittis libero in fringilla. Aenean vitae ornare massa, vel tincidunt nunc. Viv" +
            "amus venenatis leo sem, id tristique orci luctus ut.\n" +
            "Aliquam rhoncus, tellus eget ultrices aliquam," +
            " dui dui facilisis felis, et ullamcorper felis ligula ut" +
            " sem. Nulla dapibus posuere massa non scelerisque. Curabitur quam lectus, rhoncus ac pharetra eget, ultrices eu dolor. Integer id mauris enim. Qui" +
            "sque sit amet sodales est. Donec ultrices felis metus, id faucibus sapien fringilla ac. Praesent vel arcu sit amet augue rutrum semper quis sed urna.\n" +
            "Sed tempor ipsum ut tortor euismod varius. Mauris gravida feug" +
            "iat egestas. In hac habitasse platea dictumst. Aliquam erat volutpat. " +
            " molestie placerat magna, ut placerat leo sollicitudin et. Donec nec lacinia d" +
            "ui, eu commodo lorem. Donec fringilla aliquet mi, tincidunt convallis enim rutrum mole" +
            "stie. Proin nec feugiat enim. Ut tempor quam nulla, et consequat felis porttitor non. Nam nec odio a" +
            " justo interdum ullamcorper quis porttitor nisl. Mauris ut maximus dolor."+
            "Morbi sodales, nisl nec tristique finibus, enim sapien ultricies lorem, at dapibus quam dol" +

            "" +
            "or vel sapien. Nunc a quam velit. Aenean dapibus lectus at blandit suscipit. Integer eget sodales libero," +
            " sit amet laoreet est. Sed iaculis iaculis augue, et scelerisque augue scelerisque sed. Curabitur commodo, nibh in rhoncus cu" +
                    "rsus, augue leo auctor odio, quis feugiat tortor ex vitae leo. Cras ut justo gravida, rhoncus diam sit amet, susci" +
            "pit tortor. Mauris feugiat elit at dignissim faucibus. Nam malesuada suscipit mi, at euismod magna. Suspendisse feugiat nisi ex, in" +
            "faucibus libero feugiat non. Quisque suscipit mauris a dolor fringilla, ac mattis neque laoreet. Cras sollicitudin nibh vitae enim porttitor molestie.");



        count("streets are stone stars are not");
    }





    public static void count(String a){

        WordCounter counter = new WordCounter();

        counter.count(a);

        counter.treeForming();
    }
















}
