 public Config(final String dayShift, final String nightShift, final String dayPrice, final String dayMode,
				final String nightPrice, final String nightMode, final int exempt)
				
				
 Api.set(new Config("07:00-22:00", "22:00-07:00", "3+4", "maxHour[6, 12, 15]", "0", "once", 15))
 Config里的入餐
 dayShift：07:00-22:00
 nightShift: 22:00-07:00
 dayPrice:表示收费模式，“3+4”,首小时计费+后续小时计费；车行道计费时，按15分钟计费，设为“3”，相当于“3+3”
 dayMode：
		可选项："maxHour[6, 12, 15]", maxHour[最大时间，阶段时间，计时单位] ，maxHour[6,12,60]表示12小时以内计时上限为6小时，12小时以外累计，计时单位为60分钟，可默认为maxHour[6,12]
		可选项:"hourly",按小时计费,无计时上限限制
		可选项：minutes[n],n=60相当于hourly,按n分钟为计费单位
		可选项：once
		
nightPrice:	夜间收费价格，默认为0	
nightMode：默认once，按此收费
exempt：免费时段，默认15（分钟）
 