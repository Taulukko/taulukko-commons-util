
function i18n (ctx,dft)
{
	var context = ctx;
	var defaultText = dft;
	function getTextD(id,defaultText,args)
	{
		 
	 	 defaultText = _ext(defaultText);
	 	 args = _ext(args);
	  
	 	  
		if(!args.isArray())
		{
			alert("Args need be array");
			return null;
		}
		return getText(id,args);
		
	 
		function _ext(obj)
		{
			if(obj==null)
			{
				return obj;
			}
			
			function falseFunction ()
			{
				return false;
			}
			
			function isArray()
			{
				if(typeof(this)=="object")
				{
					return (this.length)?true:false;
				}
				
				return false;
			}
			
			obj.isArray = isArray;
			String.prototype.isArray = falseFunction;
			Number.prototype.isArray = falseFunction;
			
			return obj;
		}
		
	}
	 

	function getText(id,args)
	{
	   var ctx = getContext();
	   
		if(ctx)
		{
			if(id>=ctx.length)
			{
				id=0;
			}
			var ret = context.get(id);
			for(var cont =0;args != null && cont < args.length;cont++)
			{
				ret = replaceParam(ret,"%" + cont,args[cont]);
			}
			return ret;	
		}
		return "CONTEXT NOT LOAD";
		
		function replaceParam(string, token, newtoken) 
		{
			return string.split(token).join(newtoken);
		}

	}
}