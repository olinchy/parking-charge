 public Config(final String dayShift, final String nightShift, final String dayPrice, final String dayMode,
				final String nightPrice, final String nightMode, final int exempt)
				
				
 Api.set(new Config("07:00-22:00", "22:00-07:00", "3+4", "maxHour[6, 12, 15]", "0", "once", 15))
 Config������
 dayShift��07:00-22:00
 nightShift: 22:00-07:00
 dayPrice:��ʾ�շ�ģʽ����3+4��,��Сʱ�Ʒ�+����Сʱ�Ʒѣ����е��Ʒ�ʱ����15���ӼƷѣ���Ϊ��3�����൱�ڡ�3+3��
 dayMode��
		��ѡ�"maxHour[6, 12, 15]", maxHour[���ʱ�䣬�׶�ʱ�䣬��ʱ��λ] ��maxHour[6,12,60]��ʾ12Сʱ���ڼ�ʱ����Ϊ6Сʱ��12Сʱ�����ۼƣ���ʱ��λΪ60���ӣ���Ĭ��ΪmaxHour[6,12]
		��ѡ��:"hourly",��Сʱ�Ʒ�,�޼�ʱ��������
		��ѡ�minutes[n],n=60�൱��hourly,��n����Ϊ�Ʒѵ�λ
		��ѡ�once
		
nightPrice:	ҹ���շѼ۸�Ĭ��Ϊ0	
nightMode��Ĭ��once�������շ�
exempt�����ʱ�Σ�Ĭ��15�����ӣ�
 