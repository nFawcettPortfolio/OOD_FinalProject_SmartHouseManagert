/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.iterator;

/**
 *
 * @author Kami
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BtnMenu {

	List<DeviceBtn> btns;

	public BtnMenu() {
		btns = new ArrayList<DeviceBtn>();
	}

	public void addBtn(DeviceBtn b) {
		btns.add(b);
	}

	public Iterator<DeviceBtn> iterator() {
		return new MenuIterator();
	}

	class MenuIterator implements Iterator<DeviceBtn> {
		public int currentIndex = 0;
                

		@Override
		public boolean hasNext() {
			if (currentIndex >= btns.size()) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public DeviceBtn next() {
			return btns.get(currentIndex++);
		}

		@Override
		public void remove() {
			btns.remove(--currentIndex);
		}

	}

}
