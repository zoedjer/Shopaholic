package project.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dbaccess.OrderBoxTable;
import project.entity.OrderedItem;
import project.entity.ProfitNode;
import project.myUtil.MyUtil;

@Service
@Transactional
public class OrderBoxService {

    @Autowired
    private OrderBoxTable orderBoxTable;
    
    public List<OrderedItem> getOrderItems(long id) {
	return orderBoxTable.getOrderItems(id);
    }

    public long createOrder(Long userId, Long customerId, Long postId, String tracking_number, String shipping_fee,
	    List<OrderedItem> items) {
	return orderBoxTable.newOrder(userId, customerId, postId, tracking_number, shipping_fee, items);
    }

    public List<Map<String, String>> getOrderDetail(Long id) {
	return orderBoxTable.getOrderDetail(id);
    }

    public void removeOrder(long... ids) {
	orderBoxTable.removeOrder(ids);
    }

    public List<ProfitNode> getProfitByDate(Long id, String start, String end) {
	if (start == null || end == null || "".equals(start) || "".equals(end)) {
	    LocalDate date = LocalDate.now();
//	    return orderBoxTable.getProfitByDate(id, date.minusDays(30).toString(), date.toString());
	    return randomDataForGraph();
	} else {
	    start = MyUtil.reformatDateString(start);
	    end = MyUtil.reformatDateString(end);

	    return orderBoxTable.getProfitByDate(id, start, end);
	}
    }

    private List<ProfitNode> randomDataForGraph() {
	List<ProfitNode> list = new ArrayList<>();
	Random ran = new Random();

	for (int i = 1; i < 31; i++) {
	    list.add(new ProfitNode("2016-12-" + i, String.valueOf(ran.nextInt(300))));
	}

	return list;
    }

}
