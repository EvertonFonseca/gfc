select 
	id_animal,
	tag_animal,
	sisbov_animal,
	nome_animal,
	peso_atual_animal,
	data_nascimento_animal,
	nome_lote,
	case when nome_apartacao IS NULL THEN '' ELSE nome_apartacao END,
	case when nome_pai IS NULL THEN '' ELSE nome_pai END,
	case when nome_mae IS NULL THEN '' ELSE nome_mae END,
	case when l.id_lote IS NULL THEN 0 ELSE l.id_lote END,
        case when ap.id_apartacao IS NULL THEN 0 ELSE ap.id_apartacao END,
	case when fa.id_familia IS NULL THEN 0 ELSE fa.id_familia END,
	case when fa.nome_familia IS NULL THEN '' ELSE fa.nome_familia END,
	case when id_pai IS NULL THEN 0 ELSE id_pai END,
	case when id_mae IS NULL THEN 0 ELSE id_mae END,
	case when an.id_apartacao IS NULL THEN 0 ELSE an.id_apartacao END
 from animais an 
	left join (select id_lote,nome_lote from lotes) l on l.id_lote = an.id_lote
	left join (select id_apartacao,nome_apartacao from apartacoes) ap on ap.id_apartacao = an.id_apartacao
	left join(select fa.id_familia,fa.nome_familia,pai.id_animal AS id_pai,pai.nome_animal AS nome_pai,mae.id_animal AS id_mae,mae.nome_animal AS nome_mae
	from familia fa 
	left join animais pai on fa.id_animal_pai = pai.id_animal
	left join animais mae on fa.id_animal_mae = mae.id_animal) fa on fa.id_familia = an.id_familia ORDER BY an.id_animal;

select fa.id_familia,pai.nome_animal AS nome_pai,mae.nome_animal AS nome_mae 
	from familia fa,animais pai,animais mae where fa.id_animal_pai = pai.id_animal AND  fa.id_animal_pai = mae.id_animal;

select fa.id_familia,pai.nome_animal AS nome_pai,mae.nome_animal AS nome_mae
	from familia fa 
	left join animais pai on fa.id_animal_pai = pai.id_animal
	left join animais mae on fa.id_animal_mae = mae.id_animal;

select * from animais a left join(select fa.id_familia,pai.nome_animal AS nome_pai,mae.nome_animal AS nome_mae
	from familia fa 
	left join animais pai on fa.id_animal_pai = pai.id_animal
	left join animais mae on fa.id_animal_mae = mae.id_animal) fa on a.id_familia = fa.id_familia;

select * from animais a 
	left join(select fa.id_familia,an.nome_animal as animal_pai,an.nome_animal as animal_mae 
	from familia fa,animais an 
	where fa.id_animal_pai = an.id_animal AND fa.id_animal_mae = an.id_animal) fa on a.id_familia = fa.id_familia;