package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dbaccess.ItemTable;
import project.entity.Item;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemTable itemTab;

    public List<Item> getAll(Long userId) {
	return itemTab.getAll(userId);
    }

    public long createItem(Long userId, String name, String category, String price, String brand) {
	return itemTab.insert(userId, name, category, price, brand);
    }

    public void editItem(Long id, String name, String category, String price, String brand) {
	itemTab.edit(id, name, category, price, brand);
    }

    public void removeItem(long... ids) {
	itemTab.remove(ids);
    }

}
