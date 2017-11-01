package cn.aigestudio.datepicker.bizs.languages;

/**
 * 中文的默认实现类
 * 如果你想实现更多的语言请参考Language{@link DPLManager}
 * <p>
 * The implementation class of chinese.
 * You can refer to Language{@link DPLManager} if you want to define more language.
 *
 * @author AigeStudio 2015-03-28
 */
public class CN extends DPLManager {
    /**
     * 月份标题显示
     * <p>
     * Titles of month
     *
     * @return 长度为12的月份标题数组 Array in 12 length of month titles
     */
    @Override
    public String[] titleMonth() {
        return new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    }

    /**
     * 确定按钮文本
     *
     * Text of ensure button
     *
     * @return Text of ensure button
     */
    @Override
    public String titleEnsure() {
        return "确定";
    }
    /**
     * 公元前文本
     *
     * Text of B.C.
     *
     * @return Text of B.C.
     */
    @Override
    public String titleBC() {
        return "公元前";
    }

    /**
     * 星期标题显示
     *
     * Titles of week
     *
     * @return 长度为7的星期标题数组 Array in 7 length of week titles
     */
    @Override
    public String[] titleWeek() {
        return new String[]{"日", "一", "二", "三", "四", "五", "六"};
    }
}
