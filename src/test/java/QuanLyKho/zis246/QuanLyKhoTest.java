package QuanLyKho.zis246;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuanLyKhoTest {

    public QuanLyKho quanLyKho;
    @BeforeEach
    public void setUp() {
        quanLyKho = new QuanLyKho();
        quanLyKho.setVisible(true);
    }
    @Test
    public void testButtonSua() {

        // Thiết lập dữ liệu và mô hình cho bảng
        Object[][] data = {
                {"1", "Sản phẩm 1", "Danh mục 1", "10.5", "Mô tả 1"},
                {"2", "Sản phẩm 2", "Danh mục 2", "20.5", "Mô tả 2"},
                {"3", "Sản phẩm 3", "Danh mục 1", "15.0", "Mô tả 3"},
                {"4", "Sản phẩm 4", "Danh mục 2", "30.0", "Mô tả 4"}
        };
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"STT", "Tên", "Danh mục", "Giá", "Mô tả"});
        quanLyKho.jTable1.setModel(model);
        JTable jTable1 = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(jTable1);
        JFrame testFrame = new JFrame();
        testFrame.getContentPane().add(scrollPane);
        testFrame.pack();
        testFrame.setVisible(true);

        // Chọn hàng thứ hai (index 1)
//
        jTable1.setRowSelectionInterval(1, 1);

        // Tạo và kích hoạt sự kiện cho nút "Sửa"
        JButton buttonSua = new JButton();
        buttonSua.addActionListener(evt -> {
            quanLyKho.buttonSuaActionPerformed(evt, jTable1, testFrame);
        });

        buttonSua.doClick(); // Giả lập click nút

        // Giả lập người dùng nhập liệu trong hộp thoại chỉnh sửa
        for (Frame frame : JFrame.getFrames()) {
            if (frame instanceof JFrame && frame.getTitle().equals("Sửa thông tin sản phẩm")) {
                for (Component component : ((JFrame) frame).getContentPane().getComponents()) {
                    if (component instanceof JPanel) {
                        JPanel panel = (JPanel) component;
                        Component[] components = panel.getComponents();
                        JTextField tenSPField = (JTextField) components[1];
                        JComboBox<String> danhMucComboBox = (JComboBox<String>) components[3];
                        JTextField giaField = (JTextField) components[5];
                        JTextField moTaField = (JTextField) components[7];

                        // Chỉnh sửa dữ liệu
                        tenSPField.setText("Sản phẩm mới");
                        danhMucComboBox.setSelectedItem("Sản phẩm loại 1");
                        giaField.setText("25.0");
                        moTaField.setText("Mô tả mới");

                        JButton saveButton = (JButton) components[8];
                        saveButton.doClick(); // Giả lập click nút lưu
                    }
                }
            }
        }

        // Kiểm tra dữ liệu trong bảng
        assert "Sản phẩm mới".equals(jTable1.getValueAt(1, 1));
        assert "Sản phẩm loại 1".equals(jTable1.getValueAt(1, 2));
        assert "25.0".equals(jTable1.getValueAt(1, 3));
        assert "Mô tả mới".equals(jTable1.getValueAt(1, 4));

        // Dọn dẹp
        testFrame.dispose();
    }

    @Test
    public void testButtonThem() {
        // Thiết lập dữ liệu đầu vào
        quanLyKho.tenSP.setText("Sản phẩm 1");
        quanLyKho.danhMucSP.setSelectedItem("Sản phẩm loại 1");
        quanLyKho.giaSP.setText("10.5");
        quanLyKho.moTaSanPham.setText("Mô tả 1");

        // Mô phỏng sự kiện nhấn nút
        quanLyKho.buttonThemActionPerformed(null);

        // Lấy mô hình bảng và kiểm tra dữ liệu
        DefaultTableModel model = (DefaultTableModel) quanLyKho.jTable1.getModel();
        assertEquals("1", model.getValueAt(0, 0).toString()); // STT
        assertEquals("Sản phẩm 1", model.getValueAt(0, 1)); // TÊN
        assertEquals("Sản phẩm loại 1", model.getValueAt(0, 2)); // DANH MỤC
        assertEquals("10.5", model.getValueAt(0, 3).toString()); // GIÁ
        assertEquals("Mô tả 1", model.getValueAt(0, 4)); // MÔ TẢ
    }







    @Test
    public void testButtonXoa() {
        // Thêm một hàng vào bảng
        DefaultTableModel model = (DefaultTableModel) quanLyKho.jTable1.getModel();
        model.addRow(new Object[]{"1", "Sản phẩm 1", "Sản phẩm loại 1", "10.5", "Mô tả 1"});

        // Chọn hàng vừa thêm
        quanLyKho.jTable1.setRowSelectionInterval(0, 0);

        // Mô phỏng sự kiện nhấn nút
        quanLyKho.buttonXoaActionPerformed(null);

        // Kiểm tra rằng hàng đã được xóa khỏi bảng
        assertEquals(0, model.getRowCount());
    }


    @Test
    public void testTimKiemSanPham() {

        // Tạo một mảng đối tượng để sử dụng làm dữ liệu cho bảng
        Object[][] data = {
                {"1", "Sản phẩm 1", "Danh mục 1", "10.5", "Mô tả 1"},
                {"2", "Sản phẩm 2", "Danh mục 2", "20.5", "Mô tả 2"},
                {"3", "Sản phẩm 3", "Danh mục 1", "15.0", "Mô tả 3"},
                {"4", "Sản phẩm 4", "Danh mục 2", "30.0", "Mô tả 4"}
        };

        // Tạo một DefaultTableModel và thêm dữ liệu vào đó
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"STT", "Tên", "Danh mục", "Giá", "Mô tả"});
        quanLyKho.jTable1.setModel(model);

        // Thực hiện tìm kiếm với các thông tin tìm kiếm đã biết trước
        String ten = "Sản phẩm 1";
        String danhMuc = "Danh mục 1";
        String gia = "10.5";
        String moTa = "Mô tả 1";
        quanLyKho.timKiemSanPham(ten, danhMuc, gia, moTa);

        // Kiểm tra xem có sản phẩm tìm thấy trong bảng không
        DefaultTableModel resultModel = (DefaultTableModel) quanLyKho.jTable1.getModel();
        assertEquals("Sản phẩm 1", resultModel.getValueAt(0, 1));
        assertEquals("Danh mục 1", resultModel.getValueAt(0, 2));
        assertEquals("10.5", resultModel.getValueAt(0, 3));
        assertEquals("Mô tả 1", resultModel.getValueAt(0, 4));

    }
}
