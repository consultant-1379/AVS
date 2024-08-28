class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'avs', action: 'create')
		"500"(view:'/error')
	}
}
