package com.example.prueba_palermo.viewModel;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.prueba_palermo.model.Item;
import com.example.prueba_palermo.model.bd.AppDatabase;
import com.example.prueba_palermo.model.bd.Interface.ItemDao;
import java.util.List;

public class ItemViewModel extends ViewModel {
    private final LiveData<List<Item>> allItems;
    private ItemRepository repository;

    private MutableLiveData<Item> yerba250g = new MutableLiveData<>(new Item("Yerba 250g", 1, 5_000, 5_000));
    private MutableLiveData<Item> cigarillo10Box = new MutableLiveData<>(new Item("Cigarillo 10Box", 1, 4_500, 4_500));

    private AppDatabase database;

    public ItemViewModel(@NonNull Application application) {
        super();

        repository = new ItemRepository(application);
        allItems = repository.getAllItems();
    }

    public LiveData<Integer> getTotal() {
        return Transformations.map(allItems, items -> {
            int total = 0;
            for (Item item : items) {
                total += item.getSubtotal();
            }
            return total;
        });
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void init(Context context) {
        database = AppDatabase.getInstance(context);
    }

    public LiveData<Item> getYerba250g() {
        return yerba250g;
    }

    public LiveData<Item> getCigarillo10Box() {
        return cigarillo10Box;
    }

    public void increaseQuantity(String product) {
        Item item = getItem(product);
        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
            updateItem(item, product);
        }
    }

    public void decreaseQuantity(String product) {
        Item item = getItem(product);
        if (item != null && item.getQuantity() > 0) {
            item.setQuantity(item.getQuantity() - 1);
            updateItem(item, product);
        }
    }

    private Item getItem(String product) {
        switch (product) {
            case "Yerba 250g":
                return yerba250g.getValue();
            case "Cigarillo 10Box":
                return cigarillo10Box.getValue();
            default:
                return null;
        }
    }

    private void updateItem(Item item, String product) {
        switch (product) {
            case "Yerba 250g":
                yerba250g.setValue(item);
                break;
            case "Cigarillo 10Box":
                cigarillo10Box.setValue(item);
                break;
        }
    }

    public void insertOrUpdate(Item item) {
        repository.insert(item);
    }

    public static class ItemViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;

        public ItemViewModelFactory(Application application) {
            this.application = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ItemViewModel.class)) {
                return (T) new ItemViewModel(application);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    public class ItemRepository {
        private ItemDao itemDao;
        private LiveData<List<Item>> allItems;

        public ItemRepository(Application application) {
            AppDatabase db = AppDatabase.getInstance(application);
            itemDao = db.itemDao();
            allItems = itemDao.getAllItems();
        }

        public LiveData<List<Item>> getAllItems() {
            return allItems;
        }

        public void insert(Item item) {
            AppDatabase.getDatabaseWriteExecutor().execute(() -> {
                List<Item> existingItems = itemDao.getItemByName(item.getName());
                if (existingItems.isEmpty()) {
                    itemDao.insert(item);
                } else {
                    Item existingItem = existingItems.get(0);
                    int newQuantity = existingItem.getQuantity() + item.getQuantity();
                    int newSubtotal = newQuantity * existingItem.getPrice();
                    Item updatedItem = new Item(existingItem.getId(), existingItem.getName(), newQuantity, existingItem.getPrice());
                    updatedItem.setSubTotal(newSubtotal);
                    itemDao.update(updatedItem);
                }
            });

        }
    }

}
